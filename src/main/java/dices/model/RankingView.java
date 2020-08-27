package dices.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="ranking_view")
public class RankingView {

    @Id
    private Long id;

    @Column(name="player_name")
    private String name;

    @Column(name="entry_date")
    private LocalDate entryDate;

    @Column(name="success_rate")
    private Double successRate;

    private RankingView(){};

    public RankingView(String name, LocalDate entryDate, Double successRate) {
        this.name = name;
        this.entryDate = entryDate;
        this.successRate = successRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }
}
