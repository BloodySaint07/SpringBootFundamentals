package com.example.SpringMongoDB.Driver.pkg.service;


import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.User;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Transactional
public class UserService implements  IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private CustomExceptionService customExceptionService;

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(UserService.class);
    /** Executor */
    Executor executor= Executors.newFixedThreadPool(30);


@Override
    public void saveUser(User user)  {
        LOGGER.info("*** Inside saveUser in "+ this.getClass().getName()+" Class");
        CustomException customException=new CustomException();
        try {
            userRepository.save(user);
        }
        catch(Exception ex){
            LOGGER.info("*** Failed to Save User Details "+ this.getClass().getName()+" Class");
            customException.setErrorMessage(IErrorConstants.INVALIDREQUEST+" "+ex.getStackTrace().toString());
            customExceptionService.saveException(customException);
        }

    }

    @Override
    public String checkStat() {
        return "Service Call Working !!!";
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public List<String> getAllUsernames() throws SQLException, SQLDataException {
        List<String> userNameListResponse=new ArrayList<String>();

        // Extracting all Usernames fthrough Repository
        LOGGER.info("*** Inside getAllUsernames() in "+ this.getClass().getName()+" Class");
        ArrayList<?> userNameList= (ArrayList<?>) userRepository.findAll();
        LOGGER.info("*** RESPONSE  ***** "+userNameList);

        for(int i=0;i<userNameList.size();i++){
            Object child=userNameList.get(i);
           // LOGGER.info("*** Return Value at "+i+"  is => "+child);

            String example = child.toString();
            String unprocessedusername=example.substring(example.lastIndexOf(":") + 1);
            String userName=unprocessedusername.replaceAll("['}','\"']","");
            userNameListResponse.add(userName);
        }

        LOGGER.info("******** userNames Extracted from Database "+userNameListResponse);
        return userNameListResponse;
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("*** Inside getAllUsers in "+ this.getClass().getName()+" Class");
        CustomException customException=new CustomException();
        try {
            List<User> allUser=userRepository.findAll();
            return allUser;
        }catch (Exception ex2){
            LOGGER.info("*** Failed to Fetch User Details "+ this.getClass().getName()+" Class");
            customException.setErrorMessage(IErrorConstants.INVALIDREQUEST+" "+ex2.getStackTrace().toString());
            customExceptionService.saveException(customException);
            return null;
        }

    }


    @Async
    public String isUseNamePresent(String name) throws SQLException, SQLDataException {
        CustomException customException = new CustomException();
        final String[] fetchedUsername = new String[1];
        LOGGER.info("*** Thread Test 3 : *** " + Thread.currentThread().getName() + " *** ");
        try {
            LOGGER.info("*** Thread Test 4 : *** " + Thread.currentThread().getName() + " *** ");


            CompletableFuture<Void> futureExecutor = CompletableFuture.runAsync(
                    new Runnable() {
                        @Override
                        public void run() {
                            LOGGER.info("*** Inside Run Method "+Thread.currentThread().getName());
                            fetchedUsername[0] = userRepository.fetchUserName(name);
                        }
                    }, executor);

            LOGGER.info("*** Future Executor  " + futureExecutor.get().toString() + "*** ");

            //fetchedUsername = userRepository.fetchUserName(name);
        } catch (Exception ex3) {
            LOGGER.info("*** Failed to Extract Given Username [" + name + "]");
            customException.setErrorMessage(IErrorConstants.INVALIDREQUEST + " " + ex3.getStackTrace().toString());
            customExceptionService.saveException(customException);
            return null;
        }

        return fetchedUsername[0];
    }

}



