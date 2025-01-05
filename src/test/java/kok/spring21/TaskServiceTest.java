package kok.spring21;

import kok.spring21.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kok.spring21.models.Task;
import kok.spring21.dto.TaskDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.security.authentication.BadCredentialsException;

import kok.spring21.util.ResponseException;



import kok.spring21.TaskService;
import kok.spring21.repo.TaskRepository;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.mockito.Mockito;
import java.util.Arrays;


@Service
public class TaskServiceTest {
    private static TaskService ts;
    private static Task[]tasks=new Task[]{new Task(0,"t0","d0",0,0),new Task(1,"t1","d1",0,0),new Task(2,"t2","d2",0,0),new Task(3,"t3","d3",0,0)};
    private static boolean setStatus1(int tid,int sid){
        if(sid<0||sid>2) return false;
        try{        
            tasks[tid].setSid(sid);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private static boolean setExecutor(int tid,int uid){
        if(uid<0||uid>3) return false;
        try{        
            tasks[tid].setExecutor(uid);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private static List<Task> showStatus(int sid){
        if(sid<0||sid>2) return null;
        return Arrays.stream(tasks).filter( e->e.getSid()==sid ).collect(Collectors.toList());        
    }
    private static List<Task> showExecutor(int uid){
        if(uid<0||uid>3) return null;
        return Arrays.stream(tasks).filter( e->e.getExecutor()==uid ).collect(Collectors.toList());
    }
    @BeforeAll
    static void beforeAll(){
        System.out.println("--начало тестов TaskRepository");

        ts=new TaskService();
        TaskRepository tr1 = Mockito.mock(TaskRepository.class);
        ts.tr=tr1;

        Mockito.doAnswer(i->tasks[i.getArgument(0)]).when(tr1).get(Mockito.anyInt());

        Mockito.doAnswer(i->setStatus1(i.getArgument(0),i.getArgument(1))).when(tr1).setTaskStatus(Mockito.anyInt(),Mockito.anyInt());
        Mockito.doAnswer(i->setExecutor(i.getArgument(0),i.getArgument(1))).when(tr1).setExecutor(Mockito.anyInt(),Mockito.anyInt());

        Mockito.doAnswer(i->showStatus(i.getArgument(0))).when(tr1).show_status(Mockito.anyInt());
        Mockito.doAnswer(i->showExecutor(i.getArgument(0))).when(tr1).show_user(Mockito.anyInt());
    }
    @AfterAll
    static void afterAll(){
        System.out.println("--конец тестов TaskRepository");
    }
    @Test
    public void setStatus(){
        System.out.println(">>TEST setStatus()");
        assertEquals(true,ts.setStatus(3,1));
        assertEquals(false,ts.setStatus(0,100));
        assertEquals(false,ts.setStatus(8,0));
        assertEquals(false,ts.setStatus(5,-1));
    }
    @Test
    public void setExecutor(){
        System.out.println(">>TEST setExecutor()");
        assertEquals(true,ts.setExecutor(3,1));
        assertEquals(false,ts.setExecutor(0,100));
        assertEquals(false,ts.setExecutor(8,0));
        assertEquals(false,ts.setExecutor(5,-1));
    }

    @Test
    public void showStatus(){
        System.out.println(">>TEST showStatus()");
        List<TaskDto> l1=ts.showStatus(0);
        List<TaskDto> l2=Arrays.stream(tasks).peek(e->System.out.println(e.getSid())).filter(e->e.getSid()==0).map( t -> new TaskDto(t.getId(),t.getName(),t.getDesc(),t.getSid(),t.getExecutor(),t.getDeadline()) ).collect(Collectors.toList()) ;
        l1.stream().forEach(e->System.out.println(e.getId()));
        l2.stream().forEach(e->System.out.println(e.getId()));
        assertEquals(true,l1.equals(l2));
    }
    @Test
    public void showExecutor(){
        System.out.println(">>TEST showExecutor()");
        List<TaskDto> l1=ts.showExecutor(0);
        List<TaskDto> l2=Arrays.stream(tasks).peek(e->System.out.println(e.getExecutor())).filter(e->e.getExecutor()==0).map( t -> new TaskDto(t.getId(),t.getName(),t.getDesc(),t.getSid(),t.getExecutor(),t.getDeadline()) ).collect(Collectors.toList()) ;
        l1.stream().forEach(e->System.out.println(e.getId()));
        l2.stream().forEach(e->System.out.println(e.getId()));
        assertEquals(true,l1.equals(l2));
    }
}