package kok.spring21.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.SequenceGenerator;

import java.time.LocalDateTime;

import lombok.*;

/**
* Класс сущности задачи
*/
@Entity
@EqualsAndHashCode
@Table(name="tasks")
public class Task{

    /**
     * Идентификатор задачи
     */
    @Id
    @Column(name="id",nullable=false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sgt")
    @SequenceGenerator(name="sgt",sequenceName="task_id_seq",allocationSize=1)
    @Getter
    @Setter
    private int id;   //IDENTITY  AUTO

    /**
     * Имя задачи
     */
    @Getter
    @Setter
    @Column(name="name")
    private String name;

    /**
     * Описание задачи
     */
    @Getter
    @Setter
    @Column(name="descr")
    private String desc;


    /**
     * Идентификатор исполнителя задачи
     */
    @Getter
    @Setter
    @Column(name="uid",nullable=true)
    private Integer executor;  

    /**
     * Идентификатор статуса задачи
     */
    @Getter
    @Setter
    @Column(name="sid",nullable=false)
    private Integer sid; 

    /**
     * Дедлайн в виде строки в формате: '2025-01-10 11:30:30'
     */
    @Column(name = "deadline", columnDefinition = "TIMESTAMP")
    private LocalDateTime deadline; 

    public void setDeadline(String dt){
        try{
            deadline=LocalDateTime.parse(dt.replace(" ","T"));
        }catch(Exception e){e.printStackTrace();}
    }

    public String getDeadline(){
        if(deadline==null) return null;
        else return deadline.toString();
    }

    public Task(String name){
        this.id=id;
        this.name=name;
        executor=null;
        sid=0;     
    }

    public Task(String name,String desk){
        this.id=id;
        this.name=name;  
        this.desc=desk; 
        executor=null;
        sid=0; 
    }

    public Task(int id,String name,String desk,Integer sid,Integer uid){
        this.id=id;
        this.name=name;  
        this.desc=desk; 
        executor=uid;
        this.sid=sid; 
    }

    public Task(){executor=null;sid=0;}
}