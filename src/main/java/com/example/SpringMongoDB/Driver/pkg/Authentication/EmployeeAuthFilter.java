package com.example.SpringMongoDB.Driver.pkg.Authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class EmployeeAuthFilter extends UsernamePasswordAuthenticationFilter {

    private static String SECRET_KEY;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private static ICustomExceptionService customExceptionService;

    /**
     * Logger
     */
    static Logger LOGGER = LogManager.getLogger(EmployeeAuthFilter.class);


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

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info(" *** Username input is " + username + " & Password input is" + password + " *** ");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        // Not Model, from Spring Security Core
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY.getBytes());
        String access_token = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 6000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 6000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);


    }


}
