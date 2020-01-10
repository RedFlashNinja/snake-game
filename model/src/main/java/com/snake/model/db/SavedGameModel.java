package com.snake.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedGameModel {

    private long id;
    private String userId;
    private String nickname;
    private String snakePosition;
    private int snakeLength;
    private String frogsPosition;
}
