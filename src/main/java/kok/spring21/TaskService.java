package kok.spring21;

import kok.spring21.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kok.spring21.models.Task;
import kok.spring21.dto.TaskDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.BadCredentialsException;

import kok.spring21.util.ResponseException;
import java.time.LocalDateTime;

/**
* Класс Сервиса для реализации работы с задачами
*/
@Service
public class TaskService {
    @Autowired
    TaskRepository tr;
    /**
     * Создать задачу
     */
    @Transactional
    public void createTask(Task t){                System.out.println(">>ct-B");
        tr.save(new Task(t.getName(),t.getDesc()));
				                   System.out.println(">>ct-E");
    }
    /**
     * Установить статус задачи
     * @param tid идентификатор задачи
     * @param sid идентификатор статуса
     * @return статус установлен успешно (логическое значение)
     */
    @Transactional
    public boolean setStatus(int tid,int sid){  System.out.println(">>sts-B");
        return tr.setTaskStatus(tid,sid);
        
				                   //System.out.println(">>sts-E");
    }
    /**
     * Установить исполнителя задачи
     * @param tid идентификатор задачи
     * @param uid идентификатор исполнителя
     * @return исполнитель назначен успешно (логическое значение)
     */
    @Transactional
    public boolean setExecutor(int tid,int uid){      System.out.println(">>sts-B");
        return tr.setExecutor(tid,uid);
				                   //System.out.println(">>sts-E");
    }
    /**
     * Получить список задач по статусу
     * @param sid идентификатор статуса
     */
    @Transactional
    public List<TaskDto> showStatus(int sid){
        List<Task> tl=tr.show_status(sid);
        if(tl==null) throw new ResponseException();  
        return tl.stream().map( t -> new TaskDto(t.getId(),t.getName(),t.getDesc(),t.getSid(),t.getExecutor(),t.getDeadline()) ).collect(Collectors.toList());
    }
    /**
     * Получить список задач по исполнителю
     * @param uid идентификатор исполнителя
     */
    @Transactional
    public List<TaskDto> showExecutor(int uid){
        List<Task> tl=tr.show_user(uid);
        if(tl==null) throw new ResponseException(); //return null;   
        return tl.stream().map( t -> new TaskDto(t.getId(),t.getName(),t.getDesc(),t.getSid(),t.getExecutor(),t.getDeadline()) ).collect(Collectors.toList());
    }
    /**
     * Получить список задач по дедлайну
     * @param dt дедлайн в формате строки (пример: '2025-01-10 11:30:30')
     */
    @Transactional
    public List<TaskDto> showDeadline(String dt){
        List<Task> tl=tr.index();
        if(tl==null) throw new ResponseException(); //return null;   
        return tl.stream().filter( t -> t.getDeadline().equals(dt) ).map( t -> new TaskDto(t.getId(),t.getName(),t.getDesc(),t.getSid(),t.getExecutor(),t.getDeadline()) ).collect(Collectors.toList());
    }
}
