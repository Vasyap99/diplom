package kok.spring21.util;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import kok.spring21.AccountUserDetailsService; //
import kok.spring21.util.JwtUtil; //
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import kok.spring21.repo.TokenRepository;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountUserDetailsService customUserDetailsService;

    @Autowired
    private TokenRepository tr;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private void report_auth_err(HttpServletResponse response) throws java.io.IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(
            response.getWriter(),
            Collections.singletonMap("error","not authorized")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        if(!servletPath.startsWith("/api") || servletPath.startsWith("/api/login")  || servletPath.startsWith("/api/register")|| servletPath.startsWith("/api/logout")) {
            chain.doFilter(request, response);
            return; 
        }
        System.out.println(">>>>***** filter working");
        ///


        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null ) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            if (tr.find(jwt) && jwtUtil.validateToken(jwt, userDetails)) {
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                chain.doFilter(request, response);
            }else report_auth_err(response);
        }else{//not authorized
            report_auth_err(response);
        }
        
    }
}