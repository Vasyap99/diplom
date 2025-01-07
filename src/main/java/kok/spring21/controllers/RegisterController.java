package kok.spring21.controllers;


import kok.spring21.RegisterService;
import kok.spring21.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import kok.spring21.dto.UserDto;

/**
* Класс Контроллера регистрации для веб интерфейса
*/
@Controller
public class RegisterController {

    @Autowired
    RegisterService rs;

    @Autowired
    UserRepository ur;

    /**
     * Метод, выдающий форму для регистрации
     */
    @GetMapping("/register")
    public String register1(@ModelAttribute("u") UserDto u){
        return "register";
    }
    /**
     * Метод, принимающий данные, полученные из формы для регистрации
     */
    @PostMapping("/register")
    public String register2(@ModelAttribute("u") UserDto u){
        rs.saveUser(u);
        return "redirect:login";
    }
}
