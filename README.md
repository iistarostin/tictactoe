# Tic tac toe service
This project contains implementation of a simple SpringBoot service that allows clients to play tic tac toe.

## How to run
Prerequisites: JDK 22, Maven latest
1. Clone the project
2. In the root folder, run `mvn clean install` to build project
3. Then, run `mvn spring-boot:run` to start the service locally

The service will be running on localhost port 8080.

Alternatively, open the root folder in Intellij IDEA and start the service through _Run_ tab.

## API
The service provides 3 endpoints for basic game functionality
1. POST `/games/newGame` -- creates a new game. This method created and returns game id, which is then accepted by other APIs to interact with a particular game.
2. GET `/games/{id}` -- get details on a particular game, including board state.
3. PUT `/games/{id}` -- make a move in the game. This method accepts move parameters in request payload. 

Payload Example:
`{ "player": "X",
   "row": 2,
   "column": 2 }`

Parameters:
- player -- either 'X' or 'O'
- row and column -- indices of the next move (0-based indexing)

A postman collection for the API can be found in the project root.

## Implementation specifics
#### Data
A game board (`GameBoard` class) is represented by a 2-D array of enums: each cell is either X, O or null if the cell is empty. A game (`GameEntity` class) is represented by game id, the board and `nextToMove` field, which stores the expected next player or null if the game is ended. 

Game entities are stored by `GamesRepository` class. Multiple games can be stored and accessed at once. Finished games are stored indefinitely. All data is stored in memory and is lost on service restart. TODO: switch from custom in-memory repository to a persistent DB.

#### Flow
The endpoints mapping is done in `GameResource` class. It converts requests/responses to/from internal representation and forwards to `GameFlowService` and `GamesRepository`. `GameFlowService` performs validations of requests and changes game data according to a particular move. It also invokes `WinConditionChecker` to determine whether a game has ended.

#### Victory condition check
On service startup, the `WinConditionChecker` procedurally generates a collection of `WinCondition` templates, which correspond to row-wise, column-wise and diagonal victory conditions. When a move is made, this templates are independently applied to the new board state to determine if a game-ending position has been reached. TODO: templates may be grouped by the coordinates on a board, so that when a move is made, conditions corresponding only to the changed cell will be invoked.