package kok.spring21;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kok.spring21.repo.UserRepository;
import kok.spring21.AccountUserDetails;

@Service
public class AccountUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    @Autowired
    public AccountUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{
        return new AccountUserDetails(
            userRepository.findByName(name).orElse(null)
        );
    }
}