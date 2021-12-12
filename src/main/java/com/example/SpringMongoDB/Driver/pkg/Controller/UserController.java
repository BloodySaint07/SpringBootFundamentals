package com.example.SpringMongoDB.Driver.pkg.Controller;

import com.example.SpringMongoDB.Driver.pkg.Authentication.PasswordUtils;
import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.Dto.CustomExceptionDto;
import com.example.SpringMongoDB.Driver.pkg.Dto.EmployeeDto;
import com.example.SpringMongoDB.Driver.pkg.Dto.UserDto;
import com.example.SpringMongoDB.Driver.pkg.GeneratePDF.PDFGenerator;
import com.example.SpringMongoDB.Driver.pkg.MessagingService.MessageSender;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.Order;
import com.example.SpringMongoDB.Driver.pkg.model.SystemMessage;
import com.example.SpringMongoDB.Driver.pkg.model.User;
import com.example.SpringMongoDB.Driver.pkg.repository.IEmployeeRepository;
import com.example.SpringMongoDB.Driver.pkg.repository.IOrderRepository;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import com.example.SpringMongoDB.Driver.pkg.service.IEmployeeService;
import com.example.SpringMongoDB.Driver.pkg.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@EnableJms
public class UserController {

    /**
     * Logger
     */
    Logger LOGGER = LogManager.getLogger(UserController.class);

    /**
     * Class & Service Dependencies
     */
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustomExceptionService customExceptionService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    PasswordUtils passwordUtils;
    @Autowired
    MessageSender messageSender;

    /**
     * Additional Constants
     */

    @Value("${uploadDir}")
    private String uploadPath;
    private String FILE_UPLOAD_URL = "http://localhost:8081/api/user/upload";
    private String DOWNLOAD_URL = "http://localhost:8081/api/user/download/";
    @Value("${downloadPath}")
    private String DOWNLOAD_PATH;
    private static final int length = 8;

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/stat")
    public String checkServiceStatus() {

        LOGGER.info("******** inside checkServiceStatus ************* ");
        LOGGER.info("******** Service Object ************* [" + userService + "]");
        return userService.checkStat();
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {

        LOGGER.info("*** Inside saveUser in " + this.getClass().getName() + " Class");
        userService.saveUser(userDto.convertToUser());
        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/createEmployee")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employeeDto) {

        try {
            LOGGER.info("*** Inside saveEmployee in " + this.getClass().getName() + " Class");
            employeeService.saveEmployee(employeeDto.convertToEmployee());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UnsupportedEncodingException ex3) {
            LOGGER.info("*** FAILED TO SAVE EMPLOYEE *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDTOSAVEUSER + " " + ex3.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        } catch (Exception ex3) {
            LOGGER.info("*** FAILED TO SAVE EMPLOYEE *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDTOSAVEUSER + " " + ex3.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        }

        return null;
    }


    @SuppressWarnings("SpellCheckingInspection")
    @GetMapping("/validateCredentials")
    @ResponseBody
    @Transactional
    public ResponseEntity<?> validateCredentials(@RequestParam String username, @RequestParam String password) throws SQLException {
        LOGGER.info("*** Inside validateCredentials in " + this.getClass().getName() + " Class");
        try {
            LOGGER.info("*** Inside validateCredentials TEMP LOGGER " + " *** USERNAME *** " + employeeRepository.findUserNameByUserName(username) + " ****** " + " *** PASSWORD *** " + employeeRepository.findPasswordByUsername(username) + this.getClass().getName());
            String usernameOfEmployee = employeeRepository.findUserNameByUserName(username);
            String passwordOfEmployee = employeeRepository.findPasswordByUsername(username);

            Optional<String> salt = passwordUtils.generateSaltWithSecretKey();
            Optional<String> hashThePlainTextPassword = passwordUtils.hashThePlainTextPassword(password, salt.toString());
            boolean isPasswordMatch = passwordUtils.verifyThePlainTextPasswordWithSecretKey(passwordOfEmployee, hashThePlainTextPassword.toString(), salt.toString());
            if (isPasswordMatch) {
                return new ResponseEntity<>(usernameOfEmployee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(usernameOfEmployee, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex3) {
            LOGGER.info("*** FAILED TO GET USERNAME/PASSWORD *** ");
            CustomException customException = new CustomException(IErrorConstants.INVALIDREQUEST + " " + ex3.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        }
        return new ResponseEntity<>(IErrorConstants.FAILEDLOGIN, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/createException")
    public ResponseEntity<?> saveException(@RequestBody CustomExceptionDto customExceptiondto) {
        LOGGER.info("*** Inside saveException in " + this.getClass().getName() + " Class");

        customExceptionService.saveException(customExceptiondto.covertToCustomException());
        return new ResponseEntity<CustomExceptionDto>(customExceptiondto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves all Users", notes = " A list of Users and their basic details", response = User.class, responseContainer = "List", produces = "application/json")
    @GetMapping("/allUsers")
    @Cacheable("user-cache")
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        LOGGER.info("*** Inside getAllUsers in " + this.getClass().getName() + " Class");
        try {
            List<User> usrList = userService.getAllUsers();
            return usrList;
        } catch (Exception exc) {
            LOGGER.info("*** Exception Fired *** " + this.getClass().getName() + " Class");
            CustomException customException = new CustomException(IErrorConstants.INVALIDREQUEST + exc.getMessage().toString());
            customExceptionService.saveException(customException);
        }
        return null;

    }


    @SuppressWarnings("SpellCheckingInspection")
    @GetMapping("getallusersnames")
    public List<String> getAllUserName() throws SQLException {
        return userService.getAllUsernames();
    }

    @SuppressWarnings("SpellCheckingInspection")
    @GetMapping("/getallusersnames2")
    @ResponseBody
    @Cacheable("user-cache")
    @Transactional
    public ResponseEntity<?> getAllUserName2(@RequestParam String name) throws SQLException {
        LOGGER.info("*** Inside getAllUserName2 in " + this.getClass().getName() + " Class");
        try {
            LOGGER.info("*** Inside getAllUserName2 TEMP LOGGER " + userRepository.findUserNamesByName(name) + " ****** " + userRepository.findUserNamesByName(name).getClass());
            String usernameList = String.valueOf(userRepository.findUserNamesByName(name));
            return new ResponseEntity<>(usernameList, HttpStatus.FOUND);
        } catch (Exception ex3) {
            LOGGER.info("*** FAILED TO GET USERNAME *** ");
            CustomException customException = new CustomException(IErrorConstants.INVALIDREQUEST + " " + ex3.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        }
        return null;
    }

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody SystemMessage systemMessage) {

        LOGGER.info("*** Inside publishMessage() in " + this.getClass().getName() + " Class");
        try {
            messageSender.send(systemMessage);
            // jmsTemplate.convertAndSend("infoQueue", systemMessage);

            return new ResponseEntity<>(" *** Message Sent. *** ", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("*** FAILED TO PUBLISH MESSAGE *** ");
            CustomException customException = new CustomException(IErrorConstants.INVALIDREQUEST + " " + e.getMessage().toString().trim());
            customExceptionService.saveException(customException);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> userReport() {

        LOGGER.info("*** Inside userReport() in " + this.getClass().getName() + " Class");

        try {
            List<Order> allOrders = orderRepository.findAll();
            LOGGER.info("*** User List Fetched" + allOrders);

            ByteArrayInputStream bis = PDFGenerator.userPDFReport(allOrders);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=OrderHistory.pdf");

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            LOGGER.info("*** FAILED TO GENERATE PDF *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDPDFGEN + " " + e.getMessage().toString().trim());
            customExceptionService.saveException(customException);
            return null;
        }


    }

    @PostMapping(value = "/upload")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {

        LOGGER.info(" inside uploadFile() method  of " + this.getClass().getName());
        try {
            file.transferTo(new File(uploadPath + file.getOriginalFilename()));
            return true;
        } catch (IllegalStateException e) {
            LOGGER.info(" *** FAILED TO UPLOAD FILE *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDUPLOAD + " " + e.getMessage().toString().trim());
            customExceptionService.saveException(customException);
            return false;
        } catch (IOException e) {
            LOGGER.info(" *** FAILED TO UPLOAD FILE *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDUPLOAD + " " + e.getMessage().toString().trim());
            customExceptionService.saveException(customException);
            return false;
        } catch (Exception e) {
            LOGGER.info(" *** FAILED TO UPLOAD FILE ***");
            CustomException customException = new CustomException(IErrorConstants.FAILEDUPLOAD + " " + e.getMessage().toString().trim());
            customExceptionService.saveException(customException);
            return false;
        }


    }

    @GetMapping(value = "/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) {

        LOGGER.info(" inside download() method  of " + this.getClass().getName());

        try {

            byte[] fileData = Files.readAllBytes(new File(uploadPath + fileName).toPath());
            LOGGER.info(" *** FileData *** " + fileData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info(" *** FAILED TO DOWNLOAD FILE ***");
            CustomException customException = new CustomException(IErrorConstants.FAILEDOWNLOAD + " " + e.getMessage().toString());
            customExceptionService.saveException(customException);
            return null;
        }
    }


    @PostMapping(value = "/clientUpload/{fileName}")
    void uploadClient(@PathVariable("fileName") String fileName) {

        LOGGER.info(" inside uploadClient() method  of " + this.getClass().getName());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ClassPathResource(fileName));
            LOGGER.info(" *** Body *** " + body);

            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
            LOGGER.info(" *** Entity *** " + httpEntity);
            ResponseEntity<Object> response = restTemplate.postForEntity(FILE_UPLOAD_URL, httpEntity, Object.class);
            LOGGER.info(" *** Response *** " + response);
            LOGGER.info(" *** Final Response *** " + response.getBody());
        } catch (Exception e) {
            LOGGER.info(" *** CLIENT UPLOAD: FAILED TO UPLOAD FILE  *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDUPLOAD + " " + e.getMessage().toString());
            customExceptionService.saveException(customException);

        }


    }

    @GetMapping(value = "/clientDownload/{fileName}")
    void downloadClient(@PathVariable("fileName") String fileName) {

        LOGGER.info(" inside downloadClient() method  of " + this.getClass().getName());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            LOGGER.info(" *** Download Entity *** " + httpEntity);

            ResponseEntity<byte[]> response = restTemplate.exchange(DOWNLOAD_URL + fileName, HttpMethod.GET, httpEntity, byte[].class);
            LOGGER.info(" *** Download Response *** " + response);
            Files.write(Paths.get(DOWNLOAD_PATH + fileName), response.getBody());

        } catch (Exception e) {
            LOGGER.info(" *** CLIENT DOWNLOADER: FAILED TO DOWNLOAD FILE  *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDOWNLOAD + " " + e.getMessage().toString());
            customExceptionService.saveException(customException);

        }


    }


}
