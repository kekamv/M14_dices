package dices.service;

import dices.model.Player;

import java.util.List;
import java.util.Map;

public interface IPlayerService {

    List<Player> findAllPlayers();

    Player createPlayer(String name);

    Player updatePlayer(Long playerId, String name);

    Map<String, String> findTotalAverageRanking();

    List<Player> findRankingWinner ();

    List<Player> findRankingLoser ();
}
