package com.snake.model.db.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "snake_players")
public class PlayerEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    private int gamesCount;

    @Column(nullable = false, length = 50)
    private int frogsCount;

}
