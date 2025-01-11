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

import java.time.LocalDateTime;

/**
* Класс Рест Контроллера для реализации API для работы с задачами
*/
@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    @Autowired
    private TaskService rs;

    /**
     * Создать новую задачу
     * @param name имя задачи
     * @param desc описание задачи
     */
    @GetMapping("/new")
    public void createTask(@RequestParam("name") String name, @RequestParam("desc") String desc) {
        rs.createTask(new Task(name,desc));
    }

    /**
     * Установить статус задачи
     * @param tid идентификатор задачи
     * @param sid идентификатор статуса
     */
    @GetMapping("/setStatus")
    public boolean setStatus(@RequestParam("tid") int tid, @RequestParam("status") int sid) throws Exception {
        return rs.setStatus(tid,sid);
    }

    /**
     * Установить исполнителя задачи
     * @param tid идентификатор задачи
     * @param uid идентификатор исполнителя
     */
    @GetMapping("/setExecutor")
    public boolean setExecutor(@RequestParam("tid") int tid, @RequestParam("uid") int uid) throws Exception {
        return rs.setExecutor(tid,uid);
    }

    /**
     * Установить исполнителя задачи
     * @param tid идентификатор задачи
     * @param dt дедлайн
     */
    @GetMapping("/setDeadline")
    public boolean setDeadline(@RequestParam("tid") int tid, @RequestParam("dt") String dt) throws Exception {        System.out.println(">>tc.setDeadline: "+dt);
        return rs.setDeadline(tid,dt);
    }

    /**
     * Получить список задач по статусу
     * @param sid идентификатор статуса
     */
    @GetMapping("/showStatus")
    public ResponseEntity<List<TaskDto>> showStatus(@RequestParam("status") int sid) throws Exception {
        List<TaskDto> td=rs.showStatus(sid);
        return ResponseEntity.ok(td);
    }

    /**
     * Получить список задач по исполнителю
     * @param uid идентификатор исполнителя
     */
    @GetMapping("/showExecutor")
    public ResponseEntity<List<TaskDto>> showExecutor(@RequestParam("uid") int uid) throws Exception {
        List<TaskDto> td=rs.showExecutor(uid);
        return ResponseEntity.ok(td);
    }

    /**
     * Получить список задач по дедлайну
     * @param dt дедлайн в формате строки (пример: '2025-01-10 11:30:30')
     */
    @GetMapping("/showDeadline")
    public ResponseEntity<List<TaskDto>> showDeadline(@RequestParam("dt") String dt) throws Exception {
        List<TaskDto> td=rs.showDeadline(dt);
        return ResponseEntity.ok(td);
    }

    /**
     * Обработчик исключения из метода сервиса для генерации корректного http ответа об ошибке
     */
    @ExceptionHandler
    private ResponseEntity<String> handleException(ResponseException e){
        return new ResponseEntity<>("err", HttpStatus.NOT_FOUND);
    }
}