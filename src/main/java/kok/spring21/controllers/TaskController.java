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
import kok.spring21.dto.TaskDto;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskService ts;

    @GetMapping()
    public String index(Principal principal, Model model){
        String name=principal.getName();
        model.addAttribute("tasks",ts.index(name));
        return "tasks/index";
    }

    @GetMapping("/no_user")
    public String index_no_user(Model model){
        model.addAttribute("tasks",ts.showNoExecutor());
        return "tasks/index_no_user";
    }
    
    @GetMapping("/setStatus")
    @ResponseBody
    public String setStatus(@RequestParam("tid")int tid, @RequestParam("sid")int sid, Model model){
        if(ts.setStatus(tid,sid)) return "ok";
        else return "err";
    }

    /**
     * Создать новую задачу - обработка GET запроса
     */
    @GetMapping("/new")
    public String new1(Model model){
        model.addAttribute("task",new Task());
        return "tasks/new";
    }
    /**
     * Создать новую задачу - обработка POST запроса
     */
    @PostMapping("/new")
    public String new2(@ModelAttribute("task")Task task){
        ts.createTask(task);
        return "redirect:/tasks/no_user";
    }

    /**
     * Назначить задачу исполнителю - обработка GET запроса
     */
    @GetMapping("/appoint/{tid}")
    public String appoint1(@PathVariable("tid")Integer tid, Model model){
        Task t=ts.findById(tid).get();	 System.out.println(">>tc.appoint1(): "+t.getId()+" "+t.getName()+" "+t.getDesc());
        //TaskDto t1;
        model.addAttribute("task",t);
        //model.addAttribute("tid",tid);
        return "tasks/appoint";
    }

    /**
     * Назначить задачу исполнителю - обработка POST запроса
     */
    @PostMapping("/appoint")
    public String appoint2(@ModelAttribute("task")Task t, Model model){      System.out.println(">>tc.appoint2(): "+t.getId()+" "+t.getName()+" "+t.getDesc());
        //Integer tid=t.getId();  System.out.println(">>tc.appoint2(): "+tid); 
        ts.update(t); 
        return "redirect:/tasks/no_user";
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
