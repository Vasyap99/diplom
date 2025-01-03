package kok.spring21.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="statuses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status{
    @Id
    @Column(name="id",nullable=false)   
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tgu")
    @SequenceGenerator(name="tgu",sequenceName="status_id_seq",allocationSize=1)
    private int i;

    @Column
    private String name; 
}
