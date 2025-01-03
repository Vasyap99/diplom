package kok.spring21;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;



@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping()
    public String index(Model model){
        return "admin";
    }
    @GetMapping("/1")
    public String index1(Model model){
        return "admin";
    }
    @GetMapping("/2")
    public String index2(Model model){
        return "admin";
    }
}
