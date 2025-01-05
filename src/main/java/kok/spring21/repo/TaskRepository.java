package kok.spring21.repo;

import java.util.*;

import kok.spring21.models.*;

import org.springframework.stereotype.Component;


import java.sql.*;


import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.hibernate.*;

/**
* Класс репозитория задач
*/
@Component
public class TaskRepository{
    private static int N; 
    public int getN(){return N;}


    @Autowired
    public SessionFactory sessionFactory;

    public List<Task> index(){
	Session session = sessionFactory.getCurrentSession();

	List<Task> tasks=session.createQuery("from Task",Task.class) .getResultList();
        return tasks; 
    }

    public List<Task> show_user(int uid){
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from User where i="+uid,User.class).list().size()==0)
            return null;
        return session.createQuery("from Task where uid="+uid,Task.class) .getResultList();
    }

    public List<Task> show_status(int sid){
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from Status where i="+sid,Status.class).list().size()==0)
            return null;
        return session.createQuery("from Task where sid="+sid,Task.class) .getResultList();
    }

    public void save(Task task){
        Session session = sessionFactory.getCurrentSession();
        session.save(task);
    }

    public void update(Task task,int id){
        Session session = sessionFactory.getCurrentSession();
       
        Task t=session.get(Task.class,id);

        t.setName(task.getName());
        t.setDesc(task.getDesc());
        t.setStatus(task.getStatus());
    }

    public boolean setTaskStatus(int tid,int sid){
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from Status where i="+sid,Status.class).list().size()==0)
            return false;     
        Task t=session.get(Task.class,tid);
        if(t==null) return false;
        t.setSid(sid);
        return true; 
    }

    public boolean setExecutor(int tid,int uid){      
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from User where i="+uid,User.class).list().size()==0)
            return false;
        Task t=session.get(Task.class,tid);
        if(t==null) return false;
        t.setExecutor(uid);
        return true; 
    }

    public Task get(int id){
        Session session=sessionFactory.getCurrentSession();
        Task t=session.get(Task.class,id);
        return t; 
    }

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Task task=get(id);
        session.delete(task);
    }
}