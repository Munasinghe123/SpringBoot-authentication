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

    public UserModel( String name,String password,String email){
        this.name=name;
        this.password=password;
        this.email=email;
    }
}
