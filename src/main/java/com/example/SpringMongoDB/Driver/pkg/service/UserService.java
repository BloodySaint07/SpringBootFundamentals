package com.example.SpringMongoDB.Driver.pkg.service;


import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.User;
import com.example.SpringMongoDB.Driver.pkg.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService implements  IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private CustomExceptionService customExceptionService;

    /** LOGGER */
    Logger LOGGER = LogManager.getLogger(UserService.class);


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

}



