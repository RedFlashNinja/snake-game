package com.snake.model.db.io.repository;

import com.snake.model.db.io.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {
    PlayerEntity findByNickname(String nickName);
}
