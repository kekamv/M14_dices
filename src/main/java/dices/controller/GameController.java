
package dices.controller;

import dices.model.Game;
import dices.service.IGameService;
import org.bson.Document;
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
    public ResponseEntity<List<Document>> getAllGamesByPlayerId (@PathVariable("ID") String player_id){
        return ResponseEntity.ok()
                .body(gameService.findAllGamesByPlayer_id(player_id));
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames (){
        return ResponseEntity.ok()
                .body(gameService.findAllGames());
    }

    //records a new game for player with {id}
    @PostMapping("/{ID}/games/")
    public ResponseEntity<Game> rollDicesOnce (@PathVariable("ID") String player_id, Game game) {
        return ResponseEntity.ok()
                .body(gameService.rollDicesOnce(player_id));
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteGamesByPlayer(@PathVariable("id") String playerId){

        gameService.deleteAllGamesByPlayer_id(playerId);
        return ResponseEntity.ok().build();
    }

}
