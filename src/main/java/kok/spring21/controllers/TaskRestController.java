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

import kok.spring21.dto.TaskDto;

//
import org.springframework.security.authentication.AuthenticationManager;
import kok.spring21.TaskService;
import kok.spring21.util.*;

import org.springframework.web.bind.annotation.*;
import kok.spring21.models.Task;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import kok.spring21.util.ResponseException;


@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    @Autowired
    private TaskService rs;

    @GetMapping("/new")
    public void createTask(@RequestParam("name") String name, @RequestParam("desc") String desc) {
        rs.createTask(new Task(name,desc));
    }

    @GetMapping("/setStatus")
    public boolean setStatus(@RequestParam("tid") int tid, @RequestParam("status") int sid) throws Exception {
        return rs.setStatus(tid,sid);
    }
    @GetMapping("/setExecutor")
    public boolean setExecutor(@RequestParam("tid") int tid, @RequestParam("uid") int uid) throws Exception {
        return rs.setExecutor(tid,uid);
    }

    @GetMapping("/showStatus")
    public ResponseEntity<List<TaskDto>> showStatus(@RequestParam("status") int sid) throws Exception {
        List<TaskDto> td=rs.showStatus(sid);
        return ResponseEntity.ok(td);
    }
    @GetMapping("/showExecutor")
    public ResponseEntity<List<TaskDto>> showExecutor(@RequestParam("uid") int uid) throws Exception {
        List<TaskDto> td=rs.showExecutor(uid);
        return ResponseEntity.ok(td);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(ResponseException e){
        return new ResponseEntity<>("err", HttpStatus.NOT_FOUND);
    }
}