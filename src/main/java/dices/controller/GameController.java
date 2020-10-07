package dices.controller;

import dices.model.Game;
import dices.repository.GameRepository;
import dices.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/players")
public class GameController {

    @Autowired
    IGameService gameService;

    @Autowired
    GameRepository gameRepository;

    //returns all games for player with {id}
    @GetMapping("/{ID}/games")
    public ResponseEntity<List<Game>> getAllGamesByPlayerId (@PathVariable("ID") long playerId){
        return ResponseEntity.ok()
                .body(gameService.findGamesByPlayerId(playerId));
    }

    //records a new game for player with {id}
    @PostMapping("/{ID}/games/")
    public ResponseEntity rollDicesOnce (@PathVariable("ID") long playerId, @RequestBody HashMap<String,String> body) {
        if(body.size()!=1 || !body.containsKey("new game")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
        }

        return ResponseEntity.ok()
                .body(gameService.rollDices(playerId));
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity deleteGamesByPlayer(@PathVariable("id") long playerId){

        try {
            gameService.deleteGamesByPlayer(playerId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }
}
