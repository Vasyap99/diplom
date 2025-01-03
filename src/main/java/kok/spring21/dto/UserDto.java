package kok.spring21.dto;

import lombok.*;

@Getter
@Setter
public class UserDto {
    public UserDto(){}
    public UserDto(String name, String pass, String role){
        this.name=name;
        this.pass=pass;
        this.role=role;
    }
    public String name="";
    public String pass="";
    public String role="ROLE_USER";
}
