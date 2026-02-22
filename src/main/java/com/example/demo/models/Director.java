package com.example.demo.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Directors")
@Table(name = "directors")
@PrimaryKeyJoinColumn(name = "artist_id")
public class Director extends Artist {

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();

    public Director() {
    }

    public Director(String name) {
        super(name);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
