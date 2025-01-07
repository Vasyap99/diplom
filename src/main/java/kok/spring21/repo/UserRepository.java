package kok.spring21.repo;

import kok.spring21.models.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;

import org.springframework.transaction.annotation.*;

/**
* Класс репозитория пользователей
*/
@Component
public class UserRepository{
    @Autowired
    public SessionFactory sessionFactory;

    /**
    * Найти пользователя по имени
    */
    @Transactional
    public Optional<User> findByName(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User where name='"+name+"'",User.class) .getResultList().stream().findFirst();
    }

    /**
    * Сохранить пользователя
    */
    public void save(User user){
        Session session = sessionFactory.getCurrentSession();

        session.save(user);
    }
}