package kok.spring21.config;

import kok.spring21.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;



import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import kok.spring21.AccountUserDetailsService;


//
import kok.spring21.util.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import kok.spring21.repo.TokenRepository;

@Configuration
@ComponentScan("kok.spring21")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProvider ap;

    //
    @Autowired
    private JwtUtil jwtUtil;

    
    private AccountUserDetailsService userDetailsService;

    @Autowired
    private TokenRepository tr;

    @Autowired
    public SecurityConfig(AccountUserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder a) throws Exception{
        System.out.println(">>>SC-c()");
        a.userDetailsService(userDetailsService);
    }

    //
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{

         http//.csrf().disable()
        .authorizeRequests()
          .antMatchers("/api/login","/api/register").permitAll()
        .antMatchers("/register").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .formLogin().successHandler(MyAuthenticationSuccessHandler())
        ;

        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        AuthenticationManager authenticationManager=
            http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
        return authenticationManager;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance(); ////BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler MyAuthenticationSuccessHandler(){
        return new myAuthenticationSuccessHandler();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtUtil, userDetailsService, tr);
    }
}





//обработчик успешной аутентификации(для перенаправления при успешной авторизации)
class myAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        redirectStrategy.sendRedirect(request, response, "/tasks");
    }

}

