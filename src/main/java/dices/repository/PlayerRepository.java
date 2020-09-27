package dices.repository;

import dices.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface
PlayerRepository extends JpaRepository<Player,Long> {

    Optional<Player> findByUsername (String username);

    @Query(value="SELECT AVG(g.game_score)*100 FROM games g", nativeQuery = true)
    Double findAverageSuccessRate();

}
