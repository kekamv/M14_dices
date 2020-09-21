package dices.repository;

import dices.model.Game;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, ObjectId> ,CustomGameRepository{


    List<Game> findAll();


}
