package kok.spring21.controllers;


import kok.spring21.RegisterService;
import kok.spring21.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import kok.spring21.dto.UserDto;

//
import org.springframework.security.authentication.AuthenticationManager;
import kok.spring21.AccountUserDetailsService;
import kok.spring21.util.*;

import org.springframework.web.bind.annotation.*;
import kok.spring21.models.User;
import kok.spring21.AccountUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import kok.spring21.AuthRestService;

/**
* Класс Контроллера для регистрации и аутентификации через API
*/
@RestController
@RequestMapping("/api")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountUserDetailsService userDetailsService;

    @Autowired
    private RegisterService rs;

    @Autowired
    private AuthRestService as;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepository tr;

    /**
     * Метод регистрации
     * @param name имя пользователя
     * @param pass пароль
     */
    @GetMapping("/register")
    public String registerUser(@RequestParam("name") String name, @RequestParam("pass") String pass) {
        // Encode the user's password
        ///user.setPassword(user.getPass());
        // Save the user to the database
        System.out.println(">>a-r-0"); 
        rs.saveUser(new UserDto(name,pass,"ROLE_USER"));
        System.out.println(">>a-r-1"); 
        return "User registered successfully";
    }

    /**
     * Метод авторизации
     * @param name имя пользователя
     * @param pass пароль
     * @return http ответ с jwt токеном
     */
    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam("name") String name, @RequestParam("pass") String pass) throws Exception {
        String jwt=as.loginUser(name,pass);
        if(jwt==null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else return ResponseEntity.ok(jwt);
    }

    /**
     * Метод разлогинивания по переданному токену
     */
    @GetMapping("/logout")
    public void logoutUser(HttpServletRequest r) throws Exception {
        as.logoutUser(r);
    }
}