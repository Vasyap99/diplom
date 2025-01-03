package kok.spring21.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.SequenceGenerator;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name="tasks")
public class Task{

    @Id
    @Column(name="id",nullable=false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sgt")
    @SequenceGenerator(name="sgt",sequenceName="task_id_seq",allocationSize=1)
    private int id;   //IDENTITY  AUTO

    @Column(name="name")
    private String name;

    @Column(name="descr")
    private String desc;

    @Column(name="status")
    private String status; //NEW WORK FIN

    @Column(name="uid",nullable=true)
    private Integer executor;  

    @Column(name="sid",nullable=false)
    private Integer sid;  

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

    public Task(){status="NEW"; executor=null;sid=0;}
}