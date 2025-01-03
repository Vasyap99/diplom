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

@RestController
@RequestMapping("/api")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountUserDetailsService userDetailsService;

    @Autowired
    private RegisterService rs;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepository tr;

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

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam("name") String name, @RequestParam("pass") String pass) throws Exception {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if( ((AccountUserDetails)userDetails).userIsNull()) 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, pass)
            );
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        final String jwt = jwtUtil.generateToken(userDetails);

        tr.save(jwt);
        System.out.println(">>login1:jwt<"+jwt+"> "+jwt.length());

        return ResponseEntity.ok(jwt);
    }

    @GetMapping("/logout")
    public void logoutUser(HttpServletRequest r) throws Exception {
        String authorizationHeader = r.getHeader("Authorization");
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            tr.delete(jwt);
        }
    }
}