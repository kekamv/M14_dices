package dices.service;

import dices.model.Game;
import org.bson.Document;

import java.util.List;

public interface IGameService {

    List<Game> findAllGames();

    List<Document> findAllGamesByPlayer_id(String player_id);

    Game rollDicesOnce(String playerId);

    void deleteAllGamesByPlayer_id(String id);
}
