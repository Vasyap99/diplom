package kok.spring21;

import kok.spring21.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import kok.spring21.models.User;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    UserRepository ur;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String user=a.getName();
        String pasw=a.getCredentials().toString();

        Optional<User>o=ur.findByName(user);

        User u=o.orElse(null); 
        if(u==null) throw new BadCredentialsException("err");

        String pass=u.getPass(),
               role=u.getRole();

        if(! pasw.equals(pass)) throw new BadCredentialsException("err");

        System.out.println(">>LOGINING:"+user+":"+pasw);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, pasw, List.of(new SimpleGrantedAuthority(role)));

        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> a){
        return true;
    }
}
