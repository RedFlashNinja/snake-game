package com.snake.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private long id;
    private String userId;
    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;

}