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
    @Autowired
    public SessionFactory sessionFactory;

    /**
    * Получить список задач
    * @return список задач
    */
    public List<Task> index(){
	Session session = sessionFactory.getCurrentSession();

	List<Task> tasks=session.createQuery("from Task",Task.class) .getResultList();
        return tasks; 
    }

    /**
    * Получить список задач, не назначенных пользователям
    */
    public List<Task> show_no_user(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Task where uid is null",Task.class) .getResultList();
    }

    /**
    * Получить список задач, назначенных пользователю
    * @param uid идентификатор исполнителя
    * @return список задач или null, если нет такого исполнителя
    */
    public List<Task> show_user(int uid){
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from User where i="+uid,User.class).list().size()==0)
            return null;
        return session.createQuery("from Task where uid="+uid,Task.class) .getResultList();
    }

    /**
    * Получить список задач, имеющих указанный статус
    * @param sid идентификатор статуса
    * @return список задач или null, если нет такого статуса
    */
    public List<Task> show_status(int sid){
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from Status where i="+sid,Status.class).list().size()==0)
            return null;
        return session.createQuery("from Task where sid="+sid,Task.class) .getResultList();
    }

    /**
    * Сохранить новую задачу
    */
    public void save(Task task){
        Session session = sessionFactory.getCurrentSession();
        session.save(task);
    }

    /**
    * Обновить задачу
    */
    public void update(int tid,Task task){
        Session session = sessionFactory.getCurrentSession();
       
        Task t=session.get(Task.class,tid);

        t.setName(task.getName());
        t.setDesc(task.getDesc());
        t.setSid(task.getSid());
        t.setExecutor(task.getExecutor());
        t.setDeadline(task.getDeadline());
    }

    /**
    * Установить статус задачи
    * @param tid идентификатор задачи
    * @param sid идентификатор статуса
    * @return статус установлен успешно (логическое значение)
    */
    public boolean setTaskStatus(int tid,int sid){
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from Status where i="+sid,Status.class).list().size()==0)
            return false;     
        Task t=session.get(Task.class,tid);
        if(t==null) return false;
        t.setSid(sid);
        return true; 
    }

    /**
    * Установить исполнителя задачи
    * @param tid идентификатор задачи
    * @param uid идентификатор исполнителя
    * @return исполнитель назначен успешно (логическое значение)
    */
    public boolean setExecutor(int tid,int uid){      
        Session session = sessionFactory.getCurrentSession();
        if(session.createQuery("from User where i="+uid,User.class).list().size()==0)
            return false;
        Task t=session.get(Task.class,tid);
        if(t==null) return false;
        t.setExecutor(uid);
        return true; 
    }

    /**
    * Установить дедлайн задачи
    * @param tid идентификатор задачи
    * @param dt дедлайн
    * @return дедлайн назначен успешно (логическое значение)
    */
    public boolean setDeadline(int tid,String dt){       System.out.println(">>tr.setDeadline: "+dt);
        Session session = sessionFactory.getCurrentSession();
        Task t=session.get(Task.class,tid);
        if(t==null) return false;
        t.setDeadline(dt);
        return true; 
    }

    /**
    * Получить задачу по идентификатору
    * @param id идентификатор задачи
    */
    public Optional<Task> get(int id){
        Session session=sessionFactory.getCurrentSession();
        Task t=session.get(Task.class,id);
        return Optional.ofNullable(t); 
    }

    /**
    * Удалить задачу по идентификатору
    * @param id идентификатор задачи
    */
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Task task=get(id).get();
        session.delete(task);
    }
}