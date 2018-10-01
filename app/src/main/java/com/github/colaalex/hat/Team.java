package com.github.colaalex.hat;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Team implements Serializable, Comparable<Team> {
    private String name;
    private int score;

    public Team(String name) {
        super();
        this.name = name;
        score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addPoints(int count) {
        score += count;
    }

    @Override
    public int compareTo(@NonNull Team team) {
        int compareScore = team.getScore();
        return compareScore - score;
    }
}
