package com.example.SpringMongoDB.Driver.pkg.Authentication;


import com.example.SpringMongoDB.Driver.pkg.repository.IEmployeeRepository;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import com.example.SpringMongoDB.Driver.pkg.service.IEmployeeService;
import com.example.SpringMongoDB.Driver.pkg.utilities.JwtFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class EmployeeAuthenticationConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private ICustomExceptionService customExceptionService;
    @Autowired
    private JwtFilter jwtFilter;
    //BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();


    /**
     * Logger
     */
    Logger LOGGER = LogManager.getLogger(EmployeeAuthenticationConfig.class);


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeService);

    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().
                antMatchers("/api/user/authenticateEmployee").
                permitAll().antMatchers("/api/user/createEmployeeWithPassword").permitAll()
                .antMatchers("/api/user/getAllEmployeesWithJSON2").permitAll()
                .antMatchers("/api/user/getAllEmployeesWithJSON").permitAll()
                .antMatchers("/api/user/isUserNameTaken").permitAll().
                anyRequest().
                authenticated().and().
                exceptionHandling().and().sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Primary
    @Qualifier("passwordBEncoder")
    public PasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

}
