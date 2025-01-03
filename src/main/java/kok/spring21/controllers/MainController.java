package kok.spring21;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String index(Model model){
        return "index";
    }
    @GetMapping("/hello")
    public String hello(Model model){
        return "hello";
    }
}
