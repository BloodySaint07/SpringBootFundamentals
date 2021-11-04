package com.example.SpringMongoDB.Driver.pkg.Controller;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.Dto.CustomExceptionDto;
import com.example.SpringMongoDB.Driver.pkg.Dto.UserDto;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.User;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import com.example.SpringMongoDB.Driver.pkg.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    /** Logger */
    Logger LOGGER = LogManager.getLogger(UserController.class);

    /** Class & Service Dependencies */
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustomExceptionService customExceptionService;
    @Autowired
    private IUserRepository userRepository;



    @GetMapping("/stat")
    public String checkServiceStatus(){

        LOGGER.info("******** inside checkServiceStatus ************* ");
        LOGGER.info("******** Service Object ************* ["+userService+"]");
        return userService.checkStat();
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto)  {

        LOGGER.info("*** Inside saveUser in "+ this.getClass().getName()+" Class");
         userService.saveUser(userDto.convertToUser());
        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);

    }
    @PostMapping("/createException")
    public ResponseEntity<?> saveException(@RequestBody CustomExceptionDto customExceptiondto ){
        LOGGER.info("*** Inside saveException in " + this.getClass().getName() + " Class");

        customExceptionService.saveException(customExceptiondto.covertToCustomException());
        return new ResponseEntity<CustomExceptionDto>(customExceptiondto, HttpStatus.CREATED);
    }

    @GetMapping("/allUsers")
    @Cacheable("user-cache")
    @Transactional(readOnly=true)
    public List<User> getAllUsers(){
        LOGGER.info("*** Inside getAllUsers in " + this.getClass().getName() + " Class");
        try {
            List<User> usrList = userService.getAllUsers();
            return usrList;
        }catch(Exception exc){
            LOGGER.info("*** Exception Fired *** "+this.getClass().getName() + " Class");
            CustomException customException =new CustomException(IErrorConstants.INVALIDREQUEST+exc.getMessage().toString());
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
            LOGGER.info("*** Inside getAllUserName2 TEMP LOGGER " + userRepository.findUserNamesByName(name)+" ****** "+userRepository.findUserNamesByName(name).getClass());
            String usernameList = String.valueOf(userRepository.findUserNamesByName(name));
            return  new ResponseEntity<>(usernameList, HttpStatus.FOUND);
        }catch(Exception ex3){
            LOGGER.info("*** FAILED TO GET USERNAME *** ");
            CustomException customException =new CustomException(IErrorConstants.INVALIDREQUEST+" "+ex3.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        }
        return null;
    }

}
