package com.example.SpringMongoDB.Driver.pkg.Authentication;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthSecurity /*extends WebSecurityConfigurerAdapter*/ {

    private final UserDetailsService userDetailsService;
    ///private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordUtils passwordUtils;
    //BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

//    //@Bean
//   // public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }


    //@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    //@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().anyRequest().permitAll();
        //http.addFilter(new EmployeeAuthFilter(authenticationManagerBean()));

    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

}
