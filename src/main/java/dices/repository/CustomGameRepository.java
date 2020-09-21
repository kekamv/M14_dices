package dices.repository;

import org.bson.Document;

import java.util.List;

public interface CustomGameRepository {

    public List<Document> findGamesByPlayer_id (String id);

    public void deleteAllGamesByPlayer_id (String id);

}
