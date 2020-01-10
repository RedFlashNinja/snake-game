package com.snake.client.managers;

import com.snake.client.Constants;
import com.snake.client.listener.FrogPosition;
import com.snake.client.managers.frog.FrogManager;
import com.snake.client.managers.snake.SnakeManager;
import com.snake.model.client.GameField;
import com.snake.model.db.io.entity.PlayerEntity;
import com.snake.model.db.io.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class GameManagerImpl implements GameManager {

    private SnakeManager snakeManager;
    private FrogManager frogManager;
    private FrogPosition frogScheduledListener;
    private TaskScheduler taskScheduler;

    private GameField field;
    private boolean gameStopped;
    private boolean gameLost;
    private ScheduledFuture<?> waitingForLoading;
    private PlayerEntity playerEntity = new PlayerEntity();


    private PlayerRepository playerRepository;

    @Autowired
    public void setSnakeManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }

    @Autowired
    public void setFrogManager(FrogManager frogManager) {
        this.frogManager = frogManager;
    }

    @Autowired
    public void setFrogScheduledListener(FrogPosition frogScheduledListener) {
        this.frogScheduledListener = frogScheduledListener;
    }

    @Autowired
    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public GameManagerImpl(@Value("${field.width}") float fieldWidth,
                           @Value("${field.height}") float fieldHeight) {
        int width = (int) (Constants.FIELD_CELL_SIZE * fieldWidth);
        int height = (int) (Constants.FIELD_CELL_SIZE * fieldHeight);
        this.field = new GameField(new Rectangle(0, 0, width, height));
        this.gameStopped = true;
    }

    @Override
    public GameField getField() {
        return field;
    }

    @Override
    public boolean isGameStopped() {
        return gameStopped;
    }

    @Override
    public void stopGame() {
        if(playerRepository.findByNickname("newPlayer") != null){
            playerEntity.setGamesCount(10);
            playerEntity.setFrogsCount(3);
            playerRepository.save(playerEntity);
        }
        log.info("Stopping the game...");
        this.gameStopped = true;
    }

    @Override
    public void continueGame() {
        log.info("Continuing the game...");
        gameStopped = false;
    }

    @Override
    public void startGame() {
        playerEntity.setUserId(UUID.randomUUID());
        playerEntity.setNickname("newPlayer");
        playerRepository.save(playerEntity);

        log.info("Starting the game...");

        snakeManager.createSnake();
        frogManager.createFrogs();
        snakeManager.enableDirectionChange();
        waitingForLoading = taskScheduler.scheduleAtFixedRate(() -> {
            if (frogScheduledListener.spotsReady()) {
                continueGame();
                waitingForLoading.cancel(true);
            }
        }, 1000);
    }

    @Override
    public void restartGame() {
        log.info("Restarting the game...");

        snakeManager.createSnake();
        frogManager.createFrogs();
        snakeManager.enableDirectionChange();
        waitingForLoading = taskScheduler.scheduleAtFixedRate(() -> {
            if (frogScheduledListener.spotsReady()) {
                continueGame();
                waitingForLoading.cancel(true);
            }
        }, 1000);
    }

    @Override
    public boolean isGameLost() {
        return gameLost;
    }

    @Override
    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }
}
