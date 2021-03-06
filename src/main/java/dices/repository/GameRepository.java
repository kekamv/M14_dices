package dices.repository;

import dices.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    List<Game> findGamesByPlayerId(Long playerId);

    @Override
    void delete(Game game);
}