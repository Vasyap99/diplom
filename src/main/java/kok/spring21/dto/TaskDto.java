package kok.spring21.dto;

import lombok.*;

//import java.time.LocalDateTime;

/**
* Класс DTO задачи
*/
@Getter
@Setter
@EqualsAndHashCode
public class TaskDto{
    private int id;

    private String name;

    private String desc;

    private int sid;

    private int executor;  

    private String dt;

    public TaskDto(int id,String name,String desc,int sid,int executor,String dt){
        this.id=id;
        this.name=name;  
        this.desc=desc; 
        this.sid=sid;  
        this.executor=executor;
        this.dt=dt;
    }

    public TaskDto(){}
}