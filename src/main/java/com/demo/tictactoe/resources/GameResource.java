package com.demo.tictactoe.resources;

import com.demo.tictactoe.exceptions.GameNotFoundException;
import com.demo.tictactoe.exceptions.IllegalMoveException;
import com.demo.tictactoe.model.GameEntity;
import com.demo.tictactoe.model.Move;
import com.demo.tictactoe.model.Players;
import com.demo.tictactoe.repository.GamesRepository;
import com.demo.tictactoe.service.GameFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameResource {
    private final GameFlowService gameFlowService;
    private final GamesRepository gamesRepository;

    @GetMapping("/{id}")
    public GameServiceResponse getGameBoard(@PathVariable int id) {
        try {
            GameEntity game = gamesRepository.fetchById(id);

            return GameServiceResponse.builder()
                    .id(id)
                    .board(game.getGameBoard())
                    .message(game.getNextToMove() == null
                    ? "Game is over"
                            : game.getNextToMove().getPlayerText() + " is next to move")
                    .build();
        } catch (GameNotFoundException e) {
            return GameServiceResponse.builder()
                    .id(id)
                    .errorMessage("Game not found")
                    .build();
        }
    }

    @PostMapping("/newGame")
    public GameServiceResponse createNewGame() {
        GameEntity newGame = gamesRepository.createNewGame();
        return GameServiceResponse.builder()
                .id(newGame.getId())
                .message(newGame.getNextToMove().getPlayerText() + " is next to move")
                .board(newGame.getGameBoard())
                .build();
    }

    @PutMapping("/{id}")
    public GameServiceResponse makeMove(@PathVariable int id, @RequestBody MakeMoveRequest makeMoveRequest) {
        try {
            Players player = Players.valueOf(makeMoveRequest.getPlayer());
            Move move = new Move(player, makeMoveRequest.getRow(), makeMoveRequest.getColumn());
            GameEntity game = gameFlowService.processMove(id, move);
            return GameServiceResponse.builder()
                    .id(id)
                    .board(game.getGameBoard())
                    .message(game.getNextToMove() == null
                            ? player.getPlayerText() + " is victorious!"
                            : game.getNextToMove().getPlayerText() + " is next to move")
                    .build();
        } catch (IllegalArgumentException e) {
            return GameServiceResponse.builder()
                    .id(id)
                    .errorMessage("Invalid player")
                    .build();
        } catch (GameNotFoundException e) {
            return GameServiceResponse.builder()
                    .id(id)
                    .errorMessage("Game not found")
                    .build();
        } catch (IllegalMoveException e) {
            GameEntity game = gamesRepository.fetchById(id);
            return GameServiceResponse.builder()
                    .id(id)
                    .board(game.getGameBoard())
                    .message(game.getNextToMove() == null
                            ? "Game is over"
                            : game.getNextToMove().getPlayerText() + " is next to move")
                    .errorMessage("Move is invalid: " + e.getMessage())
                    .build();
        }
    }
}
