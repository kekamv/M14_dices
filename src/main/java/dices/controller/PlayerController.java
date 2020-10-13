package dices.controller;

import dices.service.IPlayerService;
import dices.service.TokenAuthenticationService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private TokenAuthenticationService authService;

    //return all players with its average success %
    @GetMapping("/")
    public ResponseEntity<List<Document>> getAllPlayers(){
        return ResponseEntity.ok().body(playerService.findAllPlayers());
    }
   /*
    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayersWitId(){
        return ResponseEntity.ok().body(playerService.findAll());
    }

    */

    //update a player's name
    @PutMapping("/{id}")
    public ResponseEntity updatePlayer (@RequestHeader("Authorization") String authToken,@PathVariable("id") String id,
                                        @RequestBody HashMap<String,String> body){
        Set<String> mapKeys = Set.of("name", "username", "password");

        if((body.size()==3) && (body.keySet().equals(mapKeys))) {

            if (playerService.findPlayerById(id).isPresent()) {

                String username = authService.getUsernameFromRequest(authToken);
                String userToModify = playerService.findPlayerById(id).get().getUsername();

                if (!userToModify.equals(username)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                            "Please log in with your credentials");
                }
                if (playerService.nameIsDuplicatePut(id, body.get("name"))) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("A player already exists with this name");
                }
                if (playerService.usernameIsDuplicatePut(id, body.get("username"))) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("A player already exits with this username");
                }

                if((body.get("username").equals("") ||body.get("username").length()>=3) &&
                        (body.get("password").equals("") || body.get("password").length()>=8 )) {

                    return ResponseEntity.ok()
                            .body(playerService.updatePlayer(
                                    id, body.get("name"),
                                    body.get("username"), body.get("password")));

                } else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("username length must be at least 3 and password at least 8");

            } else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Player with id: " + id + " does not exist");
        }else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
        /*
        return ResponseEntity.ok()
                .body(playerService.updatePlayer(id, player));

         */
    }

    @GetMapping("/ranking")
    public ResponseEntity getAverageRanking(){
       return ResponseEntity.status(HttpStatus.OK)
        .body(playerService.globalRanking());
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity getBestRanking(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.winnerRanking());
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity getWorseRanking(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.loserRanking());
    }
}
