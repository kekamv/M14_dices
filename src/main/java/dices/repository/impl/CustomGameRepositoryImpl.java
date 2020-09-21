package dices.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import dices.repository.CustomGameRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomGameRepositoryImpl implements CustomGameRepository {

    private MongoClient mongoClient;
    private static String DICES_DATABASE;
    private MongoDatabase db;
    private static String GAMES_COLLECTION = "games";
    private static String PLAYERS_COLLECTION = "players";
    private MongoCollection<Document> gamesCollection;
    private MongoCollection<Document> playersCollection;

    @Autowired
    public CustomGameRepositoryImpl (MongoClient mongoClient, @Value("${spring.mongodb.database}") String databaseName){
        this.mongoClient=mongoClient;
        DICES_DATABASE=databaseName;
        /*CodecRegistry pojoCodecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

         */
        this.db=this.mongoClient.getDatabase(DICES_DATABASE);
                //.withCodecRegistry(pojoCodecRegistry);

        gamesCollection=db.getCollection(GAMES_COLLECTION);
        playersCollection=db.getCollection(PLAYERS_COLLECTION);
    }


    @Override
    public List<Document> findGamesByPlayer_id (String player_id) {

        List<Document> games = new ArrayList<>();
        Bson playerFilter = Filters.eq("player_id", new ObjectId(player_id));
        gamesCollection.find(playerFilter).into(games);
        return games;
    }

    @Override
    public void deleteAllGamesByPlayer_id(String id) {

        Bson playerFilter = Filters.eq("player_id", new ObjectId(id));

        DeleteResult deleteResult = gamesCollection.deleteMany(playerFilter);
    }
}
