package com.snake.managers;

import com.snake.Constants;
import com.snake.listener.FrogPosition;
import com.snake.managers.frog.FrogManager;
import com.snake.managers.snake.SnakeManager;
import com.snake.model.game.GameField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.awt.*;
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
