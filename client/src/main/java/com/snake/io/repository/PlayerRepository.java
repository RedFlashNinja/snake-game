package com.snake.io.repository;

import com.snake.io.entity.PlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {
    PlayerEntity findByID(String userId);
    PlayerEntity findByNickName(String nickName);
}
