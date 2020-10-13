package dices.service;

import dices.model.Player;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPlayerService {

    List<Document> findAllPlayers();

    List<Player> findAll();

    Optional<Player> findPlayerById (String playerId);

    Optional<Player> findByUsername (String username);

    Player createPlayer(String name, String username, String password);

    Player updatePlayer(String id, String name, String username, String password);

    Map<String,Document> globalRanking();

    Map<String,List<Document>> winnerRanking();

    Map<String,List<Document>> loserRanking();

    boolean nameIsDuplicatePost(String name);

    boolean usernameIsDuplicatePost(String username);

    boolean nameIsDuplicatePut(String playerId, String name);

    boolean usernameIsDuplicatePut(String playerId, String username);
}
