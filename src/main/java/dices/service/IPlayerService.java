package dices.service;

import dices.model.Player;

import java.util.List;
import java.util.Optional;

public interface IPlayerService {

    Optional<Player> findPlayerById(Long playerId);

    List<Player> findAllPlayers();

    Player createPlayer(Player player);

    Player updatePlayer(Long playerId, Player player);

    String findTotalAverageRanking();

}
