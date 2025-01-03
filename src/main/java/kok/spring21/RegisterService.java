package kok.spring21;

import kok.spring21.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kok.spring21.models.User;
import kok.spring21.dto.UserDto;

import org.springframework.transaction.annotation.Transactional;

//import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;


@Service
public class RegisterService {
    @Autowired
    UserRepository ur;
    @Transactional
    public void saveUser(UserDto u){   System.out.println(">>su-B");
        if(ur.findByName(u.getName()).isPresent()) throw new BadCredentialsException("user exists!");
				    System.out.println(">>su-1");
        ur.save(new User(u.getName(),u.getPass()));
				    System.out.println(">>su-E");
    }
}
