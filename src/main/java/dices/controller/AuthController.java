package dices.controller;

import dices.model.AuthRequest;
import dices.service.IPlayerService;
import dices.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

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
    public ResponseEntity newPlayer (@RequestBody HashMap<String,String> body){

        Set<String> mapKeys = Set.of("name", "username", "password");

        if(body.size()!=3 || body.keySet()!=mapKeys){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
        }

        if (playerService.nameIsDuplicatePost(body.get("name"))) {

            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A player already exists with this name");
        }

        if(playerService.usernameIsDuplicatePost(body.get("username"))) {

            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A player already exits with this username");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.createPlayer(body.get("name"),
                        body.get("usename"),
                        body.get("password")));
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
