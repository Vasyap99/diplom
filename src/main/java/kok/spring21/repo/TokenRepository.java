package kok.spring21.repo;

import kok.spring21.models.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Component;

//import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;

//import org.springframework.transaction.annotation.*;

import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import lombok.*;
import kok.spring21.util.JwtUtil;
import kok.spring21.repo.UserRepository;
import java.util.Optional;

/**
* Класс информации о токене
*/
@Getter
@Setter
@AllArgsConstructor
class TokenInf{
    private String name;
    private int id;
}

/**
* Класс репозитория токенов
*/
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class TokenRepository{
    @Autowired
    private JwtUtil ju;

    @Autowired
    private UserRepository ur;

    private static List<TokenInf> l=new ArrayList<TokenInf>(); 

    /**
    * Найти указанное значение токена
    * @return логическое значение, токен найден
    */
    public boolean find(String token){     System.out.println(">>tr:find");        
        return l.stream().anyMatch(e->e.getName().equals(token));
    }

    /**
    * Сохранить значение токена в репозитории
    */
    public void save(String token){     System.out.println(">>tr:save");
        String name=ju.extractUsername(token);
        Optional<User> ou=ur.findByName(name);
        if(!ou.isPresent()) return;
        int id=ou.get().getI();
        //
        l.add(new TokenInf(token,id)); 
    }

    /**
    * Удалить значение токена из репозитория
    */
    public void delete(String token){     System.out.println(">>tr:del");
        String name=ju.extractUsername(token);
        Optional<User> ou=ur.findByName(name);
        if(!ou.isPresent()) return;
        int id=ou.get().getI();
        for (Iterator<TokenInf> iterator = l.iterator(); iterator.hasNext(); ) {
            TokenInf ti = iterator.next();
            if(ti.getId()==id) {
                System.out.println(">>tkr.delete:found");
                iterator.remove();
            }
        }
        //l.remove(token);
    }
}
