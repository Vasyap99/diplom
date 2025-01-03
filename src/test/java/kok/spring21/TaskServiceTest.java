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



import kok.spring21.TaskService;
import kok.spring21.repo.TaskRepository;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.mockito.Mockito;

@Service
public class TaskServiceTest {
    private static TaskService ts;
    private static int[]statuses=new int[]{0,0,0,0};
    private static int[]executors=new int[]{0,0,0,0};
    private static boolean setStatus1(int tid,int sid){
        if(sid<0||sid>2) return false;
        try{        
            statuses[tid]=sid;
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private static boolean setExecutor(int tid,int uid){
        if(uid<0||uid>3) return false;
        try{        
            executors[tid]=uid;
            return true;
        }catch(Exception e){
            return false;
        }
    }
    @BeforeAll
    static void beforeAll(){
        ts=new TaskService();
        TaskRepository tr1 = Mockito.mock(TaskRepository.class);
        ts.tr=tr1;
        Mockito.doReturn(new Task(0,"t0","d0",0,0)).when(tr1).get(0);
        Mockito.doReturn(new Task(1,"t1","d1",0,0)).when(tr1).get(1);
        Mockito.doReturn(new Task(2,"t2","d2",0,0)).when(tr1).get(2);
        Mockito.doReturn(new Task(3,"t3","d3",0,0)).when(tr1).get(3);

        for(int i=0;i<10;i++) for(int j=0;j<10;j++){
            Mockito.doReturn(setStatus1(i,j)).when(tr1).setTaskStatus(i,j);
            Mockito.doReturn(setExecutor(i,j)).when(tr1).setExecutor(i,j);
        }
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
}