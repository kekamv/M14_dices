package dices.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "`username` field is mandatory")
    @Size(min = 3, message = "`username` must be at least 3 characters long")
    @Column(name = "username")
    private String username;

    @NotNull(message = "`password` field is mandatory")
    @Size(min = 8, message = "`password` must be at least 8 characters long")
    @Column(name = "user_password")
    private String password;

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

    public Player(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
