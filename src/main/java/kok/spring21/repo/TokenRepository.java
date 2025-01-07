package kok.spring21.repo;

//import kok.spring21.models.*;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

//import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;

//import org.springframework.transaction.annotation.*;

import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
* Класс репозитория токенов
*/
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class TokenRepository{
    private static List<String> l=new ArrayList<String>(); 

    /**
    * Найти указанное значение токена
    * @return логическое значение, токен найден
    */
    public boolean find(String token){     System.out.println(">>tr:find");
        try{
            System.out.println("tr:find"+l.size());
            System.out.println(this); l.stream().forEach(System.out::println);    
            System.out.println("tr:find"+token.equals(l.get(0)));
            System.out.println("tr:find"+l.contains(token));
        }catch(Exception e){System.out.println("tr:exc");e.printStackTrace();}
        return l.contains(token);
    }

    /**
    * Сохранить значение токена в репозитории
    */
    public void save(String token){     System.out.println(">>tr:save");
        if(!l.contains(token)) {	     System.out.println(">>tr:save-ok");
            l.add(token);			    System.out.println(this); l.stream().forEach(System.out::println);            
        } 
    }

    /**
    * Удалить значение токена из репозитория
    */
    public void delete(String token){     System.out.println(">>tr:del");
        l.remove(token);
    }
}
