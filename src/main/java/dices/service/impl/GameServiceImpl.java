package dices.service.impl;

import dices.model.Dices;
import dices.model.Game;
import dices.model.Player;
import dices.repository.GameRepository;
import dices.repository.PlayerRepository;
import dices.service.IGameService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements IGameService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Document> findAllGamesByPlayer_id(String player_id) {

      Optional<Player> playerDB= playerRepository.findById(player_id);

        if(playerDB.isPresent()) {
            return gameRepository.findGamesByPlayer_id(player_id);
        }
            else throw new ResourceNotFoundException("Player with id: " + player_id + " does not exist");

    }

    @Override
    public Game rollDicesOnce(String player_id) {
        Game newGame = new Game();
        int dice1Roll= Dices.rollDices();
        int dice2Roll= Dices.rollDices();
        Dices dices= new Dices(dice1Roll, dice2Roll);
        newGame.setDices(dices);
        newGame.setGameScore();
        newGame.setPlayer_id(new ObjectId(player_id));

        gameRepository.save(newGame);

        return newGame;
    }

    @Override
    public void deleteAllGamesByPlayer_id(String id) {

            gameRepository.deleteAllGamesByPlayer_id(id);

    }
}
