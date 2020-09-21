package dices.service.impl;

import dices.model.Player;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public List<Document> findAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player createPlayer(Player player) {
        Player playerDB=new Player("name");

        if(!playerRepository.findAll().stream().map(Player::getName).collect(Collectors.toList()).contains(player.getName())) {
            if (player.getName() == null) playerDB.setName("Anonymous");
            else playerDB.setName(player.getName());

            return playerRepository.insert(playerDB);
        }

        else throw new IllegalArgumentException("A player already exists with this name");
    }

    @Override
    public Player updatePlayer(String playerId, Player player) {

      Optional<Player> playerDB = playerRepository.findById(playerId);

      if(playerDB.isPresent()){

          if(!findAll().stream().map(Player::getName).collect(Collectors.toList()).contains(player.getName())){
              Player playerUpdate = playerDB.get();

              if(player.getName()==null) playerUpdate.setName("Anonymous");
              else playerUpdate.setName(player.getName());
              return playerRepository.save(playerUpdate);
          } else throw new IllegalArgumentException("A player already exists with this name");
      } else throw new ResourceNotFoundException("Player with id: "+playerId+" does not exist");
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
}