package kok.spring21;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestParam;

import kok.spring21.repo.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import kok.spring21.models.Task;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskService ts;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("tasks",ts.index());
        return "tasks/index";
    }

    
    @GetMapping("/setStatus")
    @ResponseBody
    public String setStatus(@RequestParam("tid")int tid, @RequestParam("sid")int sid, Model model){
        if(ts.setStatus(tid,sid)) return "ok";
        else return "err";
    }

    @GetMapping("/new")
    public String new1(Model model){
        model.addAttribute("task",new Task());
        return "tasks/new";
    }

    @PostMapping("/new")
    public String new2(@ModelAttribute("task")Task task){
        ts.createTask(task);
        return "redirect:/tasks";
    }

    /*
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("person",dao.show(id).getFio());
        return "people/show";
    }

    @GetMapping("/update/{id}")
    public String new1(@PathVariable("id")int id, Model model){
        Person person=dao.get(id);
        model.addAttribute("person",person);
        model.addAttribute("id",id);
        return "people/update";
    }

    @PostMapping("/update/{id}")
    public String update2(@ModelAttribute("person")Person person,@PathVariable("id")int id){
        dao.update(person,id);
        return "redirect:/people";
    }

    @GetMapping("/delete/{id}")
    public String del(@PathVariable("id")int id, Model model){
        dao.delete(id);
        return "redirect:/people";
    }*/

}
