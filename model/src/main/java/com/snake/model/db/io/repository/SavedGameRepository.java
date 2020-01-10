package com.snake.model.db.io.repository;

import com.snake.model.db.io.entity.SavedGameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedGameRepository extends CrudRepository<SavedGameEntity, Long> {
    SavedGameEntity findSavedGameEntityByNicknameAndUserId(String nickName, String userId);
}
