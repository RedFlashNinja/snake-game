package com.snake.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerModel {

    private long id;
    private String userId;
    private String nickname;
    private int gamesCount;
    private int frogsCount;

}
