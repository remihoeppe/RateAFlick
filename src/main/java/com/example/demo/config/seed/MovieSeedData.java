package com.example.demo.config.seed;

import java.util.List;

/**
 * Static seed data for Movies. Format: (title, releaseYear, directorName, language).
 */
public final class MovieSeedData {

    private MovieSeedData() {
    }

    public static final List<MovieRow> MOVIES = List.of(
            new MovieRow("The Matrix", 1999, "The Wachowskis", "English"),
            new MovieRow("Inception", 2010, "Christopher Nolan", "English"),
            new MovieRow("The Shawshank Redemption", 1994, "Frank Darabont", "English"),
            new MovieRow("Pulp Fiction", 1994, "Quentin Tarantino", "English"),
            new MovieRow("The Dark Knight", 2008, "Christopher Nolan", "English"),
            new MovieRow("Fight Club", 1999, "David Fincher", "English"),
            new MovieRow("Forrest Gump", 1994, "Robert Zemeckis", "English"),
            new MovieRow("The Godfather", 1972, "Francis Ford Coppola", "English"),
            new MovieRow("Interstellar", 2014, "Christopher Nolan", "English"),
            new MovieRow("The Lord of the Rings: The Fellowship of the Ring", 2001, "Peter Jackson", "English"),
            new MovieRow("Parasite", 2019, "Bong Joon-ho", "Korean"),
            new MovieRow("Spirited Away", 2001, "Hayao Miyazaki", "Japanese"),
            new MovieRow("Amélie", 2001, "Jean-Pierre Jeunet", "French"),
            new MovieRow("City of God", 2002, "Fernando Meirelles", "Portuguese"),
            new MovieRow("Oldboy", 2003, "Park Chan-wook", "Korean")
    );

    public record MovieRow(String title, int releaseYear, String directorName, String language) {
    }
}
