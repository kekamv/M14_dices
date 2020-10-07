package dices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
//@EntityListeners
@Table(name="players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="player_name")
    private String name;

    @Column(name="entry_date")
    private LocalDate entryDate= LocalDate.now();

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    List<Game> games;


    @Transient
    private double successRate;



    public Player(){};

    public Player(String name) {
        this.name = name;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }



    @PostLoad
    public void calculateSuccessRate (){

        long listSize = getGames()
                .stream()
                .count();
        if (listSize==0) {
            successRate= 0.0;

        }else {
            successRate = getGames().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.averagingDouble(Game::getGameScore));

        }

        setSuccessRate(successRate);
    }

}