package com.snake.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "snake_players")
public class PlayerEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    private int gamesCount;

    @Column(nullable = false, length = 50)
    private int frogsCount;

}
