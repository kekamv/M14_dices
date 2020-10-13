package dices.repository;

import dices.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String>, CustomPlayerRepository{

    Optional<Player> findById(String id);

    Optional<Player> findByUsername(String username);

}
