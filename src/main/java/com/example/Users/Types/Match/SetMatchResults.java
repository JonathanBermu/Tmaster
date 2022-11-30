package com.example.Users.Types.Match;

import java.util.Date;

public class SetMatchResults {
    Integer id;
    Integer visitorScore;
    Integer localScore;
    Integer localScorePens;
    Integer visitorScorePens;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer matchId) {
        this.id = matchId;
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

}
