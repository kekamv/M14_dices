package dices.controller;

import dices.model.Player;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    //return all players with its average success %(esto aún pte)
    @GetMapping("/")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return ResponseEntity.ok().body(playerService.findAllPlayers());
    }
    //insert a new player in the DB
    @PostMapping
    public ResponseEntity<Player> newPlayer (@Valid @RequestBody Player player){
        return ResponseEntity.ok()
        .body(playerService.createPlayer(player));
    }

    //update a player's name
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer (@PathVariable("id") Long id,
                                                @Valid @RequestBody Player player){
        return ResponseEntity.ok()
                .body(playerService.updatePlayer(id, player));
    }

    @GetMapping("/ranking")
    public ResponseEntity getAverageRanking(){
       return ResponseEntity.status(HttpStatus.OK)
        .body(playerService.findTotalAverageRanking());
    }

}
