package com.example.SpringMongoDB.Driver.pkg.utilities;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

@Service
public class JWTGeneratorUtils {

    private static String SECRET_KEY;

    /**
     * Logger
     */
    static Logger LOGGER = LogManager.getLogger(JWTGeneratorUtils.class);

    @Autowired
    private static ICustomExceptionService customExceptionService;

    /** Extracting Key */

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




    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
