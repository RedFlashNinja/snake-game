package com.snake.model.db.service;

import com.snake.model.db.UserModel;

import java.util.List;

public interface UserService {
    UserModel createUser(UserModel user);
    UserModel getUser(String email);
    UserModel getUserByUserId(String userId);
    UserModel updateUser(String userId, UserModel user);
    void deleteUser(String userId);
    List<UserModel> getUsers(int page, int limit);
    boolean verifyEmailToken(String token);
    boolean requestPasswordReset(String email);
    boolean resetPassword(String token, String password);
}
