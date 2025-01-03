package kok.spring21.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
public class TaskDto{

    private int id;

    private String name;

    private String desc;

    private int sid;

    private int executor;  

    public TaskDto(int id,String name,String desc,int sid,int executor){
        this.id=id;
        this.name=name;  
        this.desc=desc; 
        this.sid=sid;  
        this.executor=executor;
    }

    public TaskDto(){}
}