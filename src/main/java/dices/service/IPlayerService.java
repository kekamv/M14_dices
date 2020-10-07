package dices.service;

import dices.model.Player;

import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface IPlayerService {

    Optional<Player> findPlayerById(Long playerId);

    List<Player> findAllPlayers();

    Player createPlayer(String name, String username, String password);

    Player updatePlayer(Long playerId, String name, String username, String password);

    Map<String, String> findTotalAverageRanking();

    List<Player> findRankingWinner ();

    List<Player> findRankingLoser ();

}
