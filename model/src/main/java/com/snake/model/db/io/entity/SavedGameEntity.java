package com.snake.model.db.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "snake_saved_game")
public class SavedGameEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String snakePosition;

    @Column(nullable = false, length = 50)
    private int snakeLength;

    @Column(nullable = false, length = 50)
    private String frogsPosition;

}
