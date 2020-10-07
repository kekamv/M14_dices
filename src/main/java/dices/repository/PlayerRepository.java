package dices.repository;

import dices.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface
PlayerRepository extends JpaRepository<Player,Long> {

    @Query(value="SELECT AVG(g.gameScore)*100 FROM Game g")
    Double findAverageSuccessRate();

}
