package dices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;

@Entity
//@DynamicInsert(value = true)
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="dice1_value")
    private int dice1Value;

    @Column(name="dice2_value")
    private int dice2Value;

    @Column(name="game_score")
    private int gameScore;

    @ManyToOne
    @JoinColumn(name="player_id")
    @JsonIgnore
    private Player player;

    public Game(){};

    public Game(int dice1Value, int dice2Value) {
        this.dice1Value = dice1Value;
        this.dice2Value = dice2Value;
    }

    public Game(int dice1Value, int dice2Value, int gameScore) {
        this.dice1Value = dice1Value;
        this.dice2Value = dice2Value;
        this.gameScore = gameScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDice1Value() {
        return dice1Value;
    }

    public void setDice1Value(int dice1Value) {
        this.dice1Value = dice1Value;
    }

    public int getDice2Value() {
        return dice2Value;
    }

    public void setDice2Value(int dice2Value) {
        this.dice2Value = dice2Value;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
