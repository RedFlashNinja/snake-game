package com.snake.model.db.service.impl;

import com.snake.model.db.UserModel;
import com.snake.model.db.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public UserModel createUser(UserModel user) {
        return null;
    }

    @Override
    public UserModel getUser(String email) {
        return null;
    }

    @Override
    public UserModel getUserByUserId(String userId) {
        return null;
    }

    @Override
    public UserModel updateUser(String userId, UserModel user) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserModel> getUsers(int page, int limit) {
        return null;
    }

    @Override
    public boolean verifyEmailToken(String token) {
        return false;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        return false;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        return false;
    }
}
