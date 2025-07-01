package com.oAuth.AuthService.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;


@Data               // includes getters, setters, toString, equals, hashCode
@NoArgsConstructor  // creates a no-arg constructor
@AllArgsConstructor // creates a full-arg constructor
@Document("users")
public class UserModel {

    @Id
    private String id;

    private String name;
    private String password;
    private String email;

    public UserModel( String name,String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;       
    }
}
