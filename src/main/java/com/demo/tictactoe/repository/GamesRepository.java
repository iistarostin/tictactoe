package com.demo.tictactoe.repository;

import com.demo.tictactoe.exceptions.GameNotFoundException;
import com.demo.tictactoe.model.GameBoard;
import com.demo.tictactoe.model.GameEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.demo.tictactoe.model.Players.X;

@Component
public class GamesRepository {
    private final AtomicInteger gameCounter = new AtomicInteger(0);
    private final Map<Integer, GameEntity> boardsByIds = new ConcurrentHashMap<>();

    public GameEntity createNewGame() {
        int newGameId = gameCounter.incrementAndGet();
        boardsByIds.put(newGameId,
                GameEntity.builder()
                        .id(newGameId)
                        .gameBoard(new GameBoard())
                        .nextToMove(X)
                        .build());
        return boardsByIds.get(newGameId);
    }

    public GameEntity fetchById(int gameId) {
        if (!boardsByIds.containsKey(gameId)) {
            throw new GameNotFoundException();
        }
        return boardsByIds.get(gameId);
    }

    public void update(GameEntity entity) {
        boardsByIds.put(entity.getId(), entity);
    }
}
