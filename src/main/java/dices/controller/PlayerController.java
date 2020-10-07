package dices.controller;

import dices.model.Player;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;


    //return all players with its average success %
    @GetMapping("/")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return ResponseEntity.ok().body(playerService.findAllPlayers());
    }


    /*
    Has de tindre en compte els següents detalls de construcció:
•	URL’s:
o	POST: /players : crea un jugador

     */
    //insert a new player in the DB
    @PostMapping
    public ResponseEntity newPlayer (@RequestBody HashMap<String,String> body){
        if(body.size()!=1 || !body.containsKey("name")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
        }

        return ResponseEntity.ok()
                .body(playerService.createPlayer(body.get("name")));
    }

    //update a player's name
    @PutMapping("/{id}")
    public ResponseEntity updatePlayer (@PathVariable("id") Long id,
                                        @RequestBody HashMap<String,String> body){
        if(body.size()!=1 || !body.containsKey("name")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
        }

        return ResponseEntity.ok()
                .body(playerService.updatePlayer(id, body.get("name")));
    }

    @GetMapping("/ranking")
    public ResponseEntity getAverageRanking(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.findTotalAverageRanking());
    }


    @GetMapping("/ranking/winner")
    public ResponseEntity getBestRanking(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(playerService.findRankingWinner());
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity getWorseRanking(){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(playerService.findRankingLoser());
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}
