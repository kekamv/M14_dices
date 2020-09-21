package dices.repository;

import org.bson.Document;

import java.util.List;
import java.util.Map;

public interface CustomPlayerRepository {

    List<Document> findAllPlayers();

    Map<String,Document> globalRanking();

    Map<String,List<Document>> winnerRanking();

    Map<String,List<Document>> loserRanking();

}
