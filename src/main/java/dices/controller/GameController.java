package dices.controller;

import dices.model.Game;
import dices.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class GameController {

    @Autowired
    IGameService gameService;

    //returns all games for player with {id}
    @GetMapping("/{ID}/games")
    public ResponseEntity<List<Game>> getAllGamesByPlayerId (@PathVariable("ID") long playerId){
        return ResponseEntity.ok()
                .body(gameService.findGamesByPlayerId(playerId));
    }

    //records a new game for player with {id}
    @PostMapping("/{ID}/games/")
    public ResponseEntity<Game> rollDicesOnce (@PathVariable("ID") long playerId, Game game) {
        return ResponseEntity.ok()
                .body(gameService.rollDices(playerId, game));
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteGamesByPlayer(@PathVariable("id") long playerId){

        gameService.deleteGamesByPlayer(playerId);
        return ResponseEntity.ok().build();
    }
}
