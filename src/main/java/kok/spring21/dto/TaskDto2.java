package kok.spring21.dto;

import lombok.*;

//import java.time.LocalDateTime;

/**
* Класс DTO задачи
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TaskDto2{
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
    private Integer sid;

    /**
     * Cтатус
     */
    private String String;

    /**
     * Идентификатор исполнителя
     */
    private Integer executor;  

    /**
     * Дедлайн в виде строки в формате: '2025-01-10 11:30:30'
     */
    private String dt;

}