package dices.service;

import dices.model.Player;
import org.bson.Document;

import java.util.List;
import java.util.Map;

public interface IPlayerService {

    List<Document> findAllPlayers();

    List<Player> findAll();

    Player createPlayer(Player player);

    Player updatePlayer(String id, Player player);

    Map<String,Document> globalRanking();

    Map<String,List<Document>> winnerRanking();

    Map<String,List<Document>> loserRanking();
}
