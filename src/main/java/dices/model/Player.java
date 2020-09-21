
package dices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.util.Collection;


@Document(collection = "players")
public class Player {
    @JsonProperty("_id")
    private String id;
    private String name;
    private LocalDate entryDate= LocalDate.now();
    @JsonIgnore
    private Collection<Game> game;

    public Player(){super();}

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Collection<Game> getGame() {
        return game;
    }

    public void setGame(Collection<Game> game) {
        this.game = game;
    }
}


