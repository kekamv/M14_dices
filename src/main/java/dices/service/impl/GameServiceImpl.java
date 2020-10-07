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

        List<Game> gamesList= gameRepository.findGamesByPlayerId(playerId);
        gameRepository.deleteAll(gamesList);

    }

    //record a new game for a given player
    @Override
    public Game rollDices(long playerId) {

        Game gameDB = new Game(1,1);
        int dice1 = ThreadLocalRandom.current().nextInt(1, 7);
        int dice2 = ThreadLocalRandom.current().nextInt(1, 7);
        int gameScore = (dice1 + dice2 == 7) ? 1 : 0;

        gameDB.setDice1Value(dice1);
        gameDB.setDice2Value(dice2);
        gameDB.setGameScore(gameScore);
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

