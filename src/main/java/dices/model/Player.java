package dices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Cacheable(value = false)
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

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    //(, cascade = CascadeType.ALL, orphanRemoval = true) alternativa @PreRemove como en el ejemplo al final
    @OrderColumn(name="game_number", nullable =false)
    List<Game> games;

    @Transient
    private double successRate;

    private Player(){};

    public Player(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
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

/*
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "educations")
    public Set<UserProfile> getUserProfiles() {
        return this.userProfiles;
    }

    @PreRemove
    private void removeEducationFromUsersProfile() {
        for (UsersProfile u : usersProfiles) {
            u.getEducationses().remove(this);
        }
    }

 */
}
