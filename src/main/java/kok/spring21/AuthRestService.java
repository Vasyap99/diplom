package kok.spring21;

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

import org.springframework.stereotype.Service;

@Service
public class AuthRestService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountUserDetailsService userDetailsService;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepository tr;

    public String loginUser(String name, String pass) throws Exception {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if( ((AccountUserDetails)userDetails).userIsNull()) 
            return null;

        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, pass)
            );
        }catch(Exception e){
            return null;
        }

        final String jwt = jwtUtil.generateToken(userDetails);

        tr.save(jwt);
        System.out.println(">>login1:jwt<"+jwt+"> "+jwt.length());

        return jwt;
    }

    public void logoutUser(HttpServletRequest r) throws Exception {
        String authorizationHeader = r.getHeader("Authorization");
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            tr.delete(jwt);
        }
    }
}