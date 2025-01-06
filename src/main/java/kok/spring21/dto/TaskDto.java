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
    /**
     * Идентификатор задачи
     */
    private int id;

    /**
     * Имя задачи
     */
    private String name;

    /**
     * Описание задачи
     */
    private String desc;

    /**
     * Идентификатор статуса
     */
    private int sid;

    /**
     * Идентификатор исполнителя
     */
    private int executor;  

    /**
     * Дедлайн в виде строки в формате: '2025-01-10 11:30:30'
     */
    private String dt;

    /**
     * Конструктор с аргументами
     */
    public TaskDto(int id,String name,String desc,int sid,int executor,String dt){
        this.id=id;
        this.name=name;  
        this.desc=desc; 
        this.sid=sid;  
        this.executor=executor;
        this.dt=dt;
    }

    /**
     * Конструктор без аргументов
     */
    public TaskDto(){}
}