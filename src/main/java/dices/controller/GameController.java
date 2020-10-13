
package dices.controller;

import dices.service.IGameService;
import dices.service.IPlayerService;
import dices.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/players")
public class GameController {

    @Autowired
    IGameService gameService;

    @Autowired
    IPlayerService playerService;

    @Autowired
    TokenAuthenticationService authService;

    //returns all games for player with {id}
    @GetMapping("/{id}/games")
    public ResponseEntity getAllGamesByPlayerId (@RequestHeader("Authorization") String authToken,
                                                 @PathVariable("id") String playerId){
        if(playerService.findPlayerById(playerId).isPresent()) {

            String username = authService.getUsernameFromRequest(authToken);
            String userToModify = playerService.findPlayerById(playerId).get().getUsername();

            if(!userToModify.equals(username)){

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        "Please log in with your credentials");
            }

            return ResponseEntity.ok()
                    .body(gameService.findAllGamesByPlayer_id(playerId));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player with id: "+playerId+" does not exist");

        /*
        return ResponseEntity.ok()
                .body(gameService.findAllGamesByPlayer_id(player_id));

         */
    }
/*
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames (){
        return ResponseEntity.ok()
                .body(gameService.findAllGames());
    }

 */

    //records a new game for player with {id}
    @PostMapping("/{id}/games/")
    public ResponseEntity rollDicesOnce (@RequestHeader("Authorization") String authToken,
                                         @PathVariable("id") String playerId,
                                         @RequestBody HashMap<String,String> body) {
        if (playerService.findPlayerById(playerId).isPresent()) {

            String username = authService.getUsernameFromRequest(authToken);
            String userToModify = playerService.findPlayerById(playerId).get().getUsername();

            if (!userToModify.equals(username)) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        "Please log in with your credentials");
            }

            if ((body.size() == 1) && (body.containsKey("new game"))) {
                return ResponseEntity.ok()
                        .body(gameService.rollDicesOnce(playerId));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Wrong data entry");

        } else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player with id: "+playerId+" does not exist");
    /*    return ResponseEntity.ok()
                .body(gameService.rollDicesOnce(playerId));

     */
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity deleteGamesByPlayer(@RequestHeader("Authorization") String authToken,
                                              @PathVariable("id") String playerId){

        if (playerService.findPlayerById(playerId).isPresent()) {

            String username = authService.getUsernameFromRequest(authToken);
            String userToModify = playerService.findPlayerById(playerId).get().getUsername();

            if (!userToModify.equals(username)) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        "Please log in with your credentials");
            }
            try {
                gameService.deleteAllGamesByPlayer_id(playerId);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
            }
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player with id: "+playerId+" does not exist");
    }
     /*   gameService.deleteAllGamesByPlayer_id(playerId);
        return ResponseEntity.ok().build();

      */
}
