package dices.controller;

import dices.model.Player;
import dices.service.IPlayerService;
import dices.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private TokenAuthenticationService authService;

    //return all players with its average success %(esto aún pte)
    @GetMapping("/")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return ResponseEntity.ok().body(playerService.findAllPlayers());
    }

    //update a player's name
    @PutMapping("/{id}")
    public ResponseEntity updatePlayer (@RequestHeader("Authorization") String authToken,@PathVariable("id") Long id,
                                                @Valid @RequestBody Player player){

        if(playerService.findPlayerById(id).isPresent()==true) {

            String username = authService.getUsernameFromRequest(authToken);
            String userToModify = playerService.findPlayerById(id).get().getUsername();

            if(!userToModify.equals(username)){

               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                       "Please log in with your own credentials");
            }

            if (playerService.findAllPlayers().stream().map(Player::getName)
                    .collect(Collectors.toList()).contains(player.getName())) {

               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A player already exists with this name");
            }

            if (!playerService.findAllPlayers().stream().map(Player::getUsername)
                    .collect(Collectors.toList()).contains(player.getUsername())) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A player already exits with this username");
            }

            return ResponseEntity.ok()
                    .body(playerService.updatePlayer(id, player));
        }
        else  return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Player with id: "+id+" does not exist");

    }

    @GetMapping("/ranking")
    public ResponseEntity getAverageRanking(){
       return ResponseEntity.status(HttpStatus.OK)
        .body(playerService.findTotalAverageRanking());
    }

}
