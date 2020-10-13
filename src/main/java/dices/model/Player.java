
package dices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collection;


@Document(collection = "players")
public class Player {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String username;
    private String password;
    private LocalDate entryDate= LocalDate.now();
    @JsonIgnore
    private Collection<Game> game;

    public Player(){super();}

    public Player(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


