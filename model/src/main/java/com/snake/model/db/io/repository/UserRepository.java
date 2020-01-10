package com.snake.model.db.io.repository;

import com.snake.model.db.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByFirstNameAndLastName(String firstName, String lastName);
    UserEntity findByUserId(String userID);
}
