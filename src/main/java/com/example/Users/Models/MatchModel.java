package com.example.Users.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MatchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_sec")
    @SequenceGenerator(sequenceName = "match_sec", allocationSize = 1, name = "match_sec")
    private Integer id;
    private Date datem;
    private Integer winner;
    private Integer visitorScore;
    private Integer localScore;
    private Integer state;
    private Integer round;
    private Integer localScorePens;
    private Integer visitorScorePens;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "visitor", nullable = true)
    private TeamModel visitor;

    @ManyToOne
    @JoinColumn(name = "local", nullable = true)
    private TeamModel local;

    public Integer getLocalScorePens() {
        return localScorePens;
    }

    public void setLocalScorePens(Integer localScorePens) {
        this.localScorePens = localScorePens;
    }

    public Integer getVisitorScorePens() {
        return visitorScorePens;
    }

    public void setVisitorScorePens(Integer visitorScorePens) {
        this.visitorScorePens = visitorScorePens;
    }


    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getVisitorScore() {
        return visitorScore;
    }

    public void setVisitorScore(Integer visitorScore) {
        this.visitorScore = visitorScore;
    }

    public Integer getLocalScore() {
        return localScore;
    }

    public void setLocalScore(Integer localScore) {
        this.localScore = localScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return datem;
    }

    public void setDate(Date date) {
        this.datem = date;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TeamModel getVisitor() {
        return visitor;
    }

    public void setVisitor(TeamModel visitor) {
        this.visitor = visitor;
    }

    public TeamModel getLocal() {
        return local;
    }

    public void setLocal(TeamModel local) {
        this.local = local;
    }

    public Integer getState() {
        return state;
    }
    public Integer getRound() {
        return round;
    }

    public void setRound(Integer ronda) {
        this.round = ronda;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
