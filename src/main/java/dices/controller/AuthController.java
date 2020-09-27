package dices.controller;

import dices.model.AuthRequest;
import dices.model.Player;
import dices.service.IPlayerService;
import dices.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private TokenAuthenticationService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public String hello(){return "hello";}

    //insert a new player in the DB
    @PostMapping("/SignUp")
    public ResponseEntity newPlayer (@Valid @RequestBody Player player){

        if (playerService.findAllPlayers().stream().map(Player::getName)
                .collect(Collectors.toList()).contains(player.getName())) {

            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A player already exists with this name");
        }

        if(!playerService.findAllPlayers().stream().map(Player::getUsername)
                .collect(Collectors.toList()).contains(player.getUsername())) {

            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A player already exits with this username");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.createPlayer(player));
    }

    //login method
    @PostMapping("/LogIn")
    public ResponseEntity login(@RequestBody AuthRequest authRequest) throws Exception{

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword()));
        }
        catch (Exception e){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        return ResponseEntity.ok().body(authService.generateToken(authRequest.getUsername()));
    }
}
