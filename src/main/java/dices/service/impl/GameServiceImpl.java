package dices.service.impl;

import dices.model.Game;
import dices.model.Player;
import dices.repository.GameRepository;
import dices.repository.PlayerRepository;
import dices.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class GameServiceImpl implements IGameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public List<Game> findGamesByPlayerId(long playerId) {

        Optional<Player> playerDB= playerRepository.findById(playerId);

        if(playerDB.isPresent()) {
          return  gameRepository.findGamesByPlayerId(playerId);
        }

        else throw new ResourceNotFoundException("Player with id: "+playerId+" does not exist");
    }

    //deletes all games for a given player
    @Override
    public void deleteGamesByPlayer(long playerId) {
        Optional<Player> playerDB= playerRepository.findById(playerId);

        if(playerDB.isPresent()){
            for (Game g: findGamesByPlayerId(playerId)) gameRepository.delete(g);
        }
        else throw new ResourceNotFoundException("Player with id: "+playerId+" does not exist");
    }

    //record a new game for a given player
    @Override
    public Game rollDices(long playerId, Game game) {

        Game gameDB = new Game(1,1);

        gameDB.setDice1Value(ThreadLocalRandom.current().nextInt(1, 7));
        gameDB.setDice2Value(ThreadLocalRandom.current().nextInt(1, 7));
        gameDB.setGameScore();
        playerRepository.findById(playerId)
                .map(player -> {
                    gameDB.setPlayer(player);
                    return gameDB;
                }).orElseThrow(()->
                        new ResourceNotFoundException(
                        "Player with id: " + playerId + " not found"));

        return gameRepository.save(gameDB);
    }

    }

