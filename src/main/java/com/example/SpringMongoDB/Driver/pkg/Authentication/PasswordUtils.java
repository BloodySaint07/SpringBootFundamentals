package com.example.SpringMongoDB.Driver.pkg.Authentication;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Properties;

@Component
public class PasswordUtils {


    /**
     * Logger
     */
    static Logger LOGGER = LogManager.getLogger(PasswordUtils.class);

    @Autowired
    private static ICustomExceptionService customExceptionService;

    public static final int ITERATIONS = 1000;
    public static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static String SECRET_KEY;

    /** Loading Properties File */
    static {
        try {
            InputStream input = new FileInputStream("D:\\Eclipse_Workspace\\SpringMySQLDB\\src\\main\\resources\\application.properties");
            Properties prop = new Properties();
            prop.load(input);
            SECRET_KEY = (prop.getProperty("SECRET_KEY"));
        } catch (FileNotFoundException e) {
            LOGGER.info(" *** Exception Fired *** ");
            CustomException customException = new CustomException(IErrorConstants.PASSWORDHASHINGERROR + e.getMessage().toString());
            customExceptionService.saveException(customException);
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.info(" *** Exception Fired *** ");
            CustomException customException = new CustomException(IErrorConstants.PASSWORDHASHINGERROR + e.getMessage().toString());
            customExceptionService.saveException(customException);
            e.printStackTrace();
        }
    }

    // Creating Salt
    private static final SecureRandom RAND = new SecureRandom();

    public static final Optional<String> generateSalt(final int length) {
        byte[] salt = new byte[length];
        RAND.nextBytes(salt);
        return Optional.of(Base64.getEncoder().encodeToString(salt));
    }


    public static final Optional<String> generateSaltWithSecretKey() throws UnsupportedEncodingException {


        LOGGER.info("*** Inside generateSaltWithSecretKey in PasswordUtils Class");
        LOGGER.info("*** SECRET_KEY " + SECRET_KEY);
        byte[] salt = SECRET_KEY.getBytes("UTF-8");
        return Optional.of(Base64.getEncoder().encodeToString(salt));

    }


    // Generate Hashed Password
    public static Optional<String> hashThePlainTextPassword(String plainTextPassword, String salt) {
        char[] chars = plainTextPassword.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
        Arrays.fill(chars, Character.MIN_VALUE);
        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Optional.of(Base64.getEncoder().encodeToString(securePassword));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {

            LOGGER.info(" *** Exception Fired *** ");
            CustomException customException = new CustomException(IErrorConstants.PASSWORDHASHINGERROR + ex.getMessage().toString());
            customExceptionService.saveException(customException);

            return Optional.empty();
        } catch (Exception e) {

            LOGGER.info("*** Exception Fired *** ");
            CustomException customException = new CustomException(IErrorConstants.PASSWORDHASHINGERROR + e.getMessage().toString());
            customExceptionService.saveException(customException);

            return Optional.empty();
        } finally {
            spec.clearPassword();
        }
    }

    //Verify Password
    public static boolean verifyThePlainTextPassword(
            String plainTextPassword,
            String hashedPassword,
            String salt) {

        Optional<String> optEncrypted = hashThePlainTextPassword(plainTextPassword, salt);
        if (!optEncrypted.isPresent()) {


            return false;
        }

        return optEncrypted.get().equals(hashedPassword);
    }

    // Verify Password with Secret Key
    public static boolean verifyThePlainTextPasswordWithSecretKey(
            String passwordFromDataBase,
            String hashedPassword,
            String salt) {

        int valueChecker = passwordFromDataBase.compareTo(hashedPassword);

        if (valueChecker != 0) {
            return false;
        }

        return true;
    }

}
