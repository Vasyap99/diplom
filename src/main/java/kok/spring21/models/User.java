package kok.spring21.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="siteuser")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name="id",nullable=false)   
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sgu")
    @SequenceGenerator(name="sgu",sequenceName="user_id_seq",allocationSize=1)
    private int i;

    @Column
    private String name;
    @Column
    private String pass;
    @Column
    private String role;

    public User(String name, String pass, String role) {
        this.name = name;
        this.pass = pass;
        this.role = role;
    }
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.role = "ROLE_USER";
    }
}
