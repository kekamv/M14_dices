package dices.service;

import dices.model.Game;

import java.util.List;

public interface IGameService {

    //return the full list of games for a given player
    List<Game> findGamesByPlayerId(long playerId);

    //delete all games for a given player
    void deleteGamesByPlayer(long playerId);

    //create a new game
    Game rollDices(long playerId);

    List<Game> findAll();

}
