package dices.service.impl;

import dices.model.Player;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Document> findAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> findPlayerById (String playerId){
        return playerRepository.findById(playerId);
    }

    @Override
    public Optional<Player> findByUsername (String username){

        return playerRepository.findByUsername(username);
    }

    @Override
    public Player createPlayer(String name, String username, String password) {
        /*
        Player playerDB=new Player("name");

        if(!playerRepository.findAll().stream().map(Player::getName).collect(Collectors.toList()).contains(player.getName())) {
            if (player.getName() == null) playerDB.setName("Anonymous");
            else playerDB.setName(player.getName());

            return playerRepository.insert(playerDB);
        }

        else throw new IllegalArgumentException("A player already exists with this name");

         */

        Player playerDB = new Player("name", "username", "password");
            if (name == "") {
                playerDB.setName("Anonymous");
            } else {
                playerDB.setName(name);
            }
            playerDB.setUsername(username);

        playerDB.setPassword(passwordEncoder.encode(password));
        playerDB.setEntryDate(LocalDate.now());
        return playerRepository.insert(playerDB);

    }

    @Override
    public Player updatePlayer(String playerId, String name, String username, String password) {
/*
      Optional<Player> playerDB = playerRepository.findById(playerId);

      if(playerDB.isPresent()){

          if(!findAll().stream().map(Player::getName).collect(Collectors.toList()).contains(player.getName())){
              Player playerUpdate = playerDB.get();

              if(player.getName()==null) playerUpdate.setName("Anonymous");
              else playerUpdate.setName(player.getName());
              return playerRepository.save(playerUpdate);
          } else throw new IllegalArgumentException("A player already exists with this name");
      } else throw new ResourceNotFoundException("Player with id: "+playerId+" does not exist");

 */

      Player playerUpdate = playerRepository.findById(playerId).get();

        if (name == "") { playerUpdate.setName("Anonymous");
        } else playerUpdate.setName(name);

        if (username != "") playerUpdate.setUsername(username);

        if (password != "")
            playerUpdate.setPassword(passwordEncoder.encode(password));

        return playerRepository.save(playerUpdate);

    }

    @Override
    public Map<String, Document> globalRanking() {
        return playerRepository.globalRanking();
    }

    @Override
    public Map<String, List<Document>> winnerRanking() {
        return playerRepository.winnerRanking();
    }

    @Override
    public Map<String, List<Document>> loserRanking() {
        return playerRepository.loserRanking();
    }


    @Override
    public boolean nameIsDuplicatePost(String name) {
        boolean nameDuplicate;
        if (!findAll().stream()
                .filter(Objects::nonNull)
                .map(Player::getName).collect(Collectors.toList()).contains(name)) {
            nameDuplicate = false;
        } else {
            nameDuplicate = true;
        }
        return nameDuplicate;
    }

    @Override
    public boolean usernameIsDuplicatePost(String username) {
        boolean usernameDuplicate;
        if (!findAll().stream()
                .filter(Objects::nonNull)
                .map(Player::getUsername).collect(Collectors.toList()).contains(username)) {
            usernameDuplicate = false;
        } else {
            usernameDuplicate = true;
        }
        return usernameDuplicate;
    }

    @Override
    public boolean nameIsDuplicatePut(String playerId, String name) {
        boolean nameDuplicate;
        if (!findAll().stream()
                .filter(Objects::nonNull)
                .filter(player -> !player.getId().equals(playerId))
                .map(Player::getName)
                .collect(Collectors.toList()).contains(name)) {
            nameDuplicate = false;
        } else {
            nameDuplicate = true;
        }
        return nameDuplicate;
    }

    @Override
    public boolean usernameIsDuplicatePut(String playerId, String username) {
        boolean usernameDuplicate;
        if (!findAll().stream()
                .filter(Objects::nonNull)
                .filter(player -> !player.getId().equals(playerId))
                .map(Player::getUsername)
                .collect(Collectors.toList()).contains(username)) {
            usernameDuplicate = false;
        } else {
            usernameDuplicate = true;
        }
        return usernameDuplicate;
    }
}