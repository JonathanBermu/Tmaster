package com.example.Users.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MatchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private Integer winner;
    private Integer visitorScore;
    private Integer localScore;
    private Integer state;
    private Integer round;
    private Integer localScorePens;
    private Integer visitorScorePens;

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

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "visitor", nullable = true)
    private TeamModel visitor;

    @ManyToOne
    @JoinColumn(name = "local", nullable = true)
    private TeamModel local;


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
        id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
