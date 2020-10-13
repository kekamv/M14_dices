package dices.controller;

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

    //insert a new player in the DB
    @PostMapping("/SignUp")
    public ResponseEntity newPlayer (@RequestBody HashMap<String,String> body){

        Set<String> mapKeys = Set.of("name", "username", "password");

        if((body.size()==3) && (body.keySet().equals(mapKeys))) {


            if(body.get("username").length()>=3 && body.get("password").length()>=8 ) {

                if (playerService.nameIsDuplicatePost(body.get("name"))) {

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("A player already exists with this name");
                }

                if (playerService.usernameIsDuplicatePost(body.get("username"))) {

                   return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("A player already exits with this username");
                }
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(playerService.createPlayer(body.get("name"),
                                body.get("username"),
                                body.get("password")));
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("username length must be at least 3 and password at least 8");
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
    }


    //login method
    @PostMapping("/LogIn")
    public ResponseEntity login(@RequestBody HashMap<String,String> body) throws Exception {

        Set<String> mapKeys = Set.of("username","password");

        if ((body.size()==2) && (body.keySet().equals(mapKeys))) {

            if (playerService.findPlayerByUsername(body.get("username")).isPresent()) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(body.get("username"),
                                body.get("password")));
                return ResponseEntity.ok().body(authService.generateToken(body.get("username")));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data entry");
    }
}
