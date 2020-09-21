
package dices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class Game {

    @JsonIgnore
    //@BsonIgnore
    @BsonId
    private String id;

    //@BsonId
    //@JsonIgnore
    //private ObjectId oid;

    private Dices dices;

    private Integer gameScore;

    //@JsonProperty("player_id")
    //@BsonProperty(value = "player_id")
    private ObjectId player_id;

    //@JsonIgnore
    //@BsonProperty(value = "player_id")
    //private ObjectId playerObjectId;

    public Game(){};

    public Game(Dices dices) {
        this.dices = dices;
    }

    public Dices getDices() {
        return dices;
    }

    public void setDices(Dices dices) {
        this.dices = dices;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        //this.oid= new ObjectId(id);
    }

    //public ObjectId getOid() {
        //return oid;
    //}

    //public void setOid(ObjectId oid) {
      //  this.oid = oid;
    //this.id=oid.toHexString();
    //}

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore() {
        gameScore = (this.dices.getValue1() + this.dices.getValue2() == 7) ? 1 : 0;
    }

    public ObjectId getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(ObjectId player_id) {
        this.player_id = player_id;
        //this.playerObjectId=new ObjectId(playerId);
    }
/*
    public ObjectId getPlayerObjectId() {
        return playerObjectId;
    }

    public void setPlayerObjectId(ObjectId playerObjectId) {
        this.playerObjectId = playerObjectId;
        this.playerId=playerObjectId.toHexString();
    }

 */
}


