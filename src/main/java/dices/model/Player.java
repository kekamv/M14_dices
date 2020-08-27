package dices.model;

import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="player_name")
    private String name;

    @Column(name="entry_date")
    private LocalDate entryDate= LocalDate.now();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name="game_number", nullable =false)
    List<Game> games;


    @Formula("(SELECT AVG(g.game_score)*100 FROM games g WHERE g.player_id = id)")
    private Double successRate;

    private Player(){};

    public Player(String name) {
        this.name = name;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
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
}
