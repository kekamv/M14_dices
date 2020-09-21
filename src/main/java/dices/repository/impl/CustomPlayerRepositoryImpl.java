package dices.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import dices.repository.CustomPlayerRepository;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

@Component
public class CustomPlayerRepositoryImpl implements CustomPlayerRepository {

    private MongoClient mongoClient;
    private static String DICES_DATABASE;
    private MongoDatabase db;
    private static String GAMES_COLLECTION = "games";
    private static String PLAYERS_COLLECTION = "players";
    private MongoCollection <Document> gamesCollection;
    private MongoCollection <Document> playersCollection;

    @Autowired
    public CustomPlayerRepositoryImpl (MongoClient mongoClient, @Value("${spring.mongodb.database}") String databaseName){
        this.mongoClient=mongoClient;
        DICES_DATABASE=databaseName;
        this.db=this.mongoClient.getDatabase(DICES_DATABASE);
        gamesCollection=this.db.getCollection(GAMES_COLLECTION);
        playersCollection=this.db.getCollection(PLAYERS_COLLECTION);
    }


    @Override
    public List<Document> findAllPlayers() {

        //List<Bson> pipeline = new ArrayList<>();
        List<Document> players = new ArrayList<>();
        List<Bson> pipeline = listPlayersBasePipeline();

        playersCollection.aggregate(pipeline).into(players);
        return players;
    }

    @Override
    public Map<String, Document> globalRanking() {

        Map<String, Document> result= new HashMap<>();

        List<Bson> pipeline =rankingBaseMethod();

        //group("Global ranking", avg("ranking", "$games.gameScore"))
        Bson group = Aggregates.group("Global ranking",
                Accumulators.avg("ranking", "$games.gameScore"));

        pipeline.add(group);

        Document resultDoc=playersCollection.aggregate(pipeline).iterator().next();
        result.put("Global ranking", resultDoc);
        return result;
    }

    @Override
    public Map<String, List<Document>> winnerRanking() {

        Map<String, List<Document>> result= new HashMap<>();

        List<Bson> pipeline = listPlayersBasePipeline();
        List<Bson> pipeline2 = listPlayersBasePipeline();
        List<Document> players = new ArrayList<>();

        //To find maxValue
        //match(ne("ranking",new BsonNull())),
        Bson matchNotNull =Aggregates.match(ne("ranking", new BsonNull()));

        // sort(descending("ranking")),
        Bson sort =Aggregates.sort(descending("ranking"));

        //limit(1L))
        Bson limit =Aggregates.limit(1);

        pipeline.add(matchNotNull);
        pipeline.add(sort);
        pipeline.add(limit);

        Document maxRankingValuePlayer = playersCollection.aggregate(pipeline).first();

        Double maxValue = (Double) maxRankingValuePlayer.get("ranking");

        //with maxValue query match ranking = maxValue
        Bson match2 = Aggregates.match(eq("ranking", maxValue));

        pipeline2.add(match2);
        playersCollection.aggregate(pipeline2).into(players);

        result.put("Winners", players);
        return result;
    }

    @Override
    public Map<String, List<Document>> loserRanking() {

        Map<String, List<Document>> result= new HashMap<>();

        List<Bson> pipeline = listPlayersBasePipeline();
        List<Bson> pipeline2 = listPlayersBasePipeline();
        List<Document> players = new ArrayList<>();

        //To find minValue
        //match(ne("ranking",new BsonNull())),
        Bson matchNotNull =Aggregates.match(ne("ranking", new BsonNull()));

        // sort(descending("ranking")),
        Bson sort =Aggregates.sort(ascending("ranking"));

        //limit(1L))
        Bson limit =Aggregates.limit(1);

        pipeline.add(matchNotNull);
        pipeline.add(sort);
        pipeline.add(limit);

        Document minRankingValuePlayer = playersCollection.aggregate(pipeline).first();
        Double minValue = (Double) minRankingValuePlayer.get("ranking");

        //with maxValue query match ranking = maxValue
        Bson match2 = Aggregates.match(eq("ranking", minValue));

        pipeline2.add(match2);
        playersCollection.aggregate(pipeline2).into(players);

        result.put("Losers", players);
        return result;
    }

    private List<Bson> rankingBaseMethod (){

        List<Bson> pipeline = new ArrayList<>();

        //(lookup("games", "_id", "player_id", "games"),
        Bson lookup = Aggregates.lookup("games", "_id", "player_id", "games");

        //unwind("$games")
        Bson unwind = Aggregates.unwind("$games");

        pipeline.add(lookup);
        pipeline.add(unwind);

        return pipeline;
    }

    private List<Bson> listPlayersBasePipeline (){
        List<Bson> pipeline = new ArrayList<>();

        //(lookup("games", "_id", "player_id", "games"),
        Bson lookup = Aggregates.lookup("games", "_id", "player_id", "games");

        //addFields(new Field("ranking",new Document("$avg", "$games.gameScore")))
        Bson addFields = Aggregates.addFields(new Field<>("ranking",
                new Document("$avg", "$games.gameScore")));

        // project(fields(excludeId(), exclude("games")))
        Bson project = Aggregates.project(fields(excludeId(), exclude("games")));

        pipeline.add(lookup);
        pipeline.add(addFields);
        pipeline.add(project);

        return pipeline;
    }

}
