package dices.service;

import dices.model.Player;

import java.util.List;

public interface IPlayerService {

    List<Player> findAllPlayers();

    Player createPlayer(Player player);

    Player updatePlayer(Long playerId, Player player);

    String findTotalAverageRanking();

}
