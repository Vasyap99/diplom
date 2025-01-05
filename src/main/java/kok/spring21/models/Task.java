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

    @Id
    @Column(name="id",nullable=false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sgt")
    @SequenceGenerator(name="sgt",sequenceName="task_id_seq",allocationSize=1)
    @Getter
    @Setter
    private int id;   //IDENTITY  AUTO

    @Getter
    @Setter
    @Column(name="name")
    private String name;

    @Getter
    @Setter
    @Column(name="descr")
    private String desc;

    @Getter
    @Setter
    @Column(name="status")
    private String status; //NEW WORK FIN

    @Getter
    @Setter
    @Column(name="uid",nullable=true)
    private Integer executor;  

    @Getter
    @Setter
    @Column(name="sid",nullable=false)
    private Integer sid; 

    @Column(name = "deadline", columnDefinition = "TIMESTAMP")
    private LocalDateTime deadline; 

    public void setDeadline(String dt){
        deadline=LocalDateTime.parse(dt);
    }

    public String getDeadline(){
        if(deadline==null) return null;
        else return deadline.toString();
    }

    public Task(String name){
        this.id=id;
        this.name=name;
        status="NEW";  
        executor=null;
        sid=0;     
    }

    public Task(String name,String desk){
        this.id=id;
        this.name=name;  
        this.desc=desk; 
        status="NEW";  
        executor=null;
        sid=0; 
    }

    public Task(int id,String name,String desk,int sid,int uid){
        this.id=id;
        this.name=name;  
        this.desc=desk; 
        executor=uid;
        this.sid=sid; 
    }

    public Task(){status="NEW"; executor=null;sid=0;}
}