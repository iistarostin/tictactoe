package com.demo.tictactoe.service;

import com.demo.tictactoe.exceptions.IllegalMoveException;
import com.demo.tictactoe.model.GameBoard;
import com.demo.tictactoe.model.GameEntity;
import com.demo.tictactoe.model.Move;
import com.demo.tictactoe.model.Players;
import com.demo.tictactoe.repository.GamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.demo.tictactoe.model.GameBoard.BOARD_SIZE;

@Service
@RequiredArgsConstructor
public class GameFlowService {
    private final GamesRepository gamesRepository;
    private final WinConditionChecker winConditionChecker;

    public GameEntity processMove(int gameId, Move move) {
        GameEntity currentGame = gamesRepository.fetchById(gameId);
        if (currentGame.getNextToMove() == null) {
            throw new IllegalMoveException("The game has already ended");
        }
        if (!(currentGame.getNextToMove() == move.getPlayer())) {
            throw new IllegalMoveException("It's other player's turn");
        }
        GameBoard board = currentGame.getGameBoard();
        validateMoveOnBoard(board, move);
        currentGame = applyMove(currentGame, move);
        gamesRepository.update(currentGame);
        return currentGame;
    }

    private void validateMoveOnBoard(GameBoard board, Move move) {
        if (isIndexOutOfRange(move.getRow(), BOARD_SIZE) ||
                isIndexOutOfRange(move.getColumn(), BOARD_SIZE)) {
            throw new IllegalMoveException("Selected cell is out of board");
        }
        if (board.getValue(move.getRow(), move.getColumn()) != null) {
            throw new IllegalMoveException("The cell is already occupied");
        }
    }

    private GameEntity applyMove(GameEntity game, Move move) {
        Players playerToMove = game.getNextToMove();
        GameBoard newBoard = game.getGameBoard()
                .withCellUpdated(move.getRow(), move.getColumn(), playerToMove);
        var newStateBuilder = game.toBuilder();
        newStateBuilder.gameBoard(newBoard);
        if (winConditionChecker.isGameEnded(newBoard)) {
            newStateBuilder.nextToMove(null);
        } else {
            newStateBuilder.nextToMove(playerToMove.getOther());
        }
        return newStateBuilder.build();
    }

    private static boolean isIndexOutOfRange(int index, int maxIndex) {
        return index < 0 || index >= maxIndex;
    }
}
