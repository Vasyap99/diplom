package kok.spring21;

import org.springframework.security.core.userdetails.UserDetails;
import kok.spring21.models.User;
import kok.spring21.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


public class AccountUserDetails implements UserDetails{
    private User user;

    public boolean userIsNull(){return user==null;}

    @Autowired
    public AccountUserDetails(User user){
        this.user=user;
    }

    @Override
    public Collection <? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public String getPassword(){
        return user.getPass();
    }

    @Override
    public String getUsername(){
        return user.getName();
    }    

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}