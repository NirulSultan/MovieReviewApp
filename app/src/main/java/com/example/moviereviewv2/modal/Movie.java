package com.example.moviereviewv2.modal;

public class Movie {
  private String title;
  private String vote_average;
  private String poster_path;
  private String description;

    public Movie() {
    }

    public Movie (String title, String vote_average, String poster_path, String description) {
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
