package com.example.demo.config.seed;

import java.util.List;

/**
 * Static seed data for Movies. Format: (title, releaseYear, directorName, language).
 * Each director from DirectorSeedData has a filmography here.
 * Actor–movie links are in {@link ActorSeedData}.
 */
public final class MovieSeedData {

    private MovieSeedData() {
    }

    public static final List<MovieRow> MOVIES = List.of(
            // The Wachowskis
            new MovieRow("The Matrix", 1999, "The Wachowskis", "English"),
            new MovieRow("The Matrix Reloaded", 2003, "The Wachowskis", "English"),
            new MovieRow("The Matrix Revolutions", 2003, "The Wachowskis", "English"),
            new MovieRow("V for Vendetta", 2005, "The Wachowskis", "English"),
            new MovieRow("Cloud Atlas", 2012, "The Wachowskis", "English"),
            // Christopher Nolan
            new MovieRow("Inception", 2010, "Christopher Nolan", "English"),
            new MovieRow("The Dark Knight", 2008, "Christopher Nolan", "English"),
            new MovieRow("Interstellar", 2014, "Christopher Nolan", "English"),
            new MovieRow("Memento", 2000, "Christopher Nolan", "English"),
            new MovieRow("The Prestige", 2006, "Christopher Nolan", "English"),
            new MovieRow("Oppenheimer", 2023, "Christopher Nolan", "English"),
            // Frank Darabont
            new MovieRow("The Shawshank Redemption", 1994, "Frank Darabont", "English"),
            new MovieRow("The Green Mile", 1999, "Frank Darabont", "English"),
            new MovieRow("The Mist", 2007, "Frank Darabont", "English"),
            // Quentin Tarantino
            new MovieRow("Pulp Fiction", 1994, "Quentin Tarantino", "English"),
            new MovieRow("Kill Bill: Vol. 1", 2003, "Quentin Tarantino", "English"),
            new MovieRow("Inglourious Basterds", 2009, "Quentin Tarantino", "English"),
            new MovieRow("Django Unchained", 2012, "Quentin Tarantino", "English"),
            new MovieRow("Once Upon a Time in Hollywood", 2019, "Quentin Tarantino", "English"),
            // David Fincher
            new MovieRow("Fight Club", 1999, "David Fincher", "English"),
            new MovieRow("Se7en", 1995, "David Fincher", "English"),
            new MovieRow("Zodiac", 2007, "David Fincher", "English"),
            new MovieRow("The Social Network", 2010, "David Fincher", "English"),
            new MovieRow("Gone Girl", 2014, "David Fincher", "English"),
            // Robert Zemeckis
            new MovieRow("Forrest Gump", 1994, "Robert Zemeckis", "English"),
            new MovieRow("Back to the Future", 1985, "Robert Zemeckis", "English"),
            new MovieRow("Back to the Future Part II", 1989, "Robert Zemeckis", "English"),
            new MovieRow("Cast Away", 2000, "Robert Zemeckis", "English"),
            // Francis Ford Coppola
            new MovieRow("The Godfather", 1972, "Francis Ford Coppola", "English"),
            new MovieRow("The Godfather Part II", 1974, "Francis Ford Coppola", "English"),
            new MovieRow("Apocalypse Now", 1979, "Francis Ford Coppola", "English"),
            new MovieRow("The Conversation", 1974, "Francis Ford Coppola", "English"),
            // Peter Jackson
            new MovieRow("The Lord of the Rings: The Fellowship of the Ring", 2001, "Peter Jackson", "English"),
            new MovieRow("The Lord of the Rings: The Two Towers", 2002, "Peter Jackson", "English"),
            new MovieRow("The Lord of the Rings: The Return of the King", 2003, "Peter Jackson", "English"),
            new MovieRow("King Kong", 2005, "Peter Jackson", "English"),
            new MovieRow("The Hobbit: An Unexpected Journey", 2012, "Peter Jackson", "English"),
            // Bong Joon-ho
            new MovieRow("Parasite", 2019, "Bong Joon-ho", "Korean"),
            new MovieRow("Memories of Murder", 2003, "Bong Joon-ho", "Korean"),
            new MovieRow("The Host", 2006, "Bong Joon-ho", "Korean"),
            new MovieRow("Snowpiercer", 2013, "Bong Joon-ho", "English"),
            // Hayao Miyazaki
            new MovieRow("Spirited Away", 2001, "Hayao Miyazaki", "Japanese"),
            new MovieRow("My Neighbor Totoro", 1988, "Hayao Miyazaki", "Japanese"),
            new MovieRow("Princess Mononoke", 1997, "Hayao Miyazaki", "Japanese"),
            new MovieRow("Howl's Moving Castle", 2004, "Hayao Miyazaki", "Japanese"),
            new MovieRow("The Wind Rises", 2013, "Hayao Miyazaki", "Japanese"),
            // Jean-Pierre Jeunet
            new MovieRow("Amélie", 2001, "Jean-Pierre Jeunet", "French"),
            new MovieRow("Delicatessen", 1991, "Jean-Pierre Jeunet", "French"),
            new MovieRow("A Very Long Engagement", 2004, "Jean-Pierre Jeunet", "French"),
            // Fernando Meirelles
            new MovieRow("City of God", 2002, "Fernando Meirelles", "Portuguese"),
            new MovieRow("The Constant Gardener", 2005, "Fernando Meirelles", "English"),
            new MovieRow("Blindness", 2008, "Fernando Meirelles", "English"),
            // Park Chan-wook
            new MovieRow("Oldboy", 2003, "Park Chan-wook", "Korean"),
            new MovieRow("Sympathy for Lady Vengeance", 2005, "Park Chan-wook", "Korean"),
            new MovieRow("The Handmaiden", 2016, "Park Chan-wook", "Korean"),
            new MovieRow("Decision to Leave", 2022, "Park Chan-wook", "Korean"),
            // Steven Spielberg
            new MovieRow("Jaws", 1975, "Steven Spielberg", "English"),
            new MovieRow("E.T. the Extra-Terrestrial", 1982, "Steven Spielberg", "English"),
            new MovieRow("Schindler's List", 1993, "Steven Spielberg", "English"),
            new MovieRow("Saving Private Ryan", 1998, "Steven Spielberg", "English"),
            new MovieRow("Jurassic Park", 1993, "Steven Spielberg", "English"),
            new MovieRow("Lincoln", 2012, "Steven Spielberg", "English"),
            // Martin Scorsese
            new MovieRow("Taxi Driver", 1976, "Martin Scorsese", "English"),
            new MovieRow("Raging Bull", 1980, "Martin Scorsese", "English"),
            new MovieRow("Goodfellas", 1990, "Martin Scorsese", "English"),
            new MovieRow("The Departed", 2006, "Martin Scorsese", "English"),
            new MovieRow("The Wolf of Wall Street", 2013, "Martin Scorsese", "English"),
            new MovieRow("Killers of the Flower Moon", 2023, "Martin Scorsese", "English"),
            // Ridley Scott
            new MovieRow("Alien", 1979, "Ridley Scott", "English"),
            new MovieRow("Blade Runner", 1982, "Ridley Scott", "English"),
            new MovieRow("Thelma & Louise", 1991, "Ridley Scott", "English"),
            new MovieRow("Gladiator", 2000, "Ridley Scott", "English"),
            new MovieRow("The Martian", 2015, "Ridley Scott", "English"),
            // James Cameron
            new MovieRow("The Terminator", 1984, "James Cameron", "English"),
            new MovieRow("Aliens", 1986, "James Cameron", "English"),
            new MovieRow("Titanic", 1997, "James Cameron", "English"),
            new MovieRow("Avatar", 2009, "James Cameron", "English"),
            new MovieRow("Avatar: The Way of Water", 2022, "James Cameron", "English"),
            // Denis Villeneuve
            new MovieRow("Arrival", 2016, "Denis Villeneuve", "English"),
            new MovieRow("Blade Runner 2049", 2017, "Denis Villeneuve", "English"),
            new MovieRow("Dune", 2021, "Denis Villeneuve", "English"),
            new MovieRow("Sicario", 2015, "Denis Villeneuve", "English"),
            new MovieRow("Prisoners", 2013, "Denis Villeneuve", "English"),
            // Alfonso Cuarón
            new MovieRow("Y Tu Mamá También", 2001, "Alfonso Cuarón", "Spanish"),
            new MovieRow("Children of Men", 2006, "Alfonso Cuarón", "English"),
            new MovieRow("Gravity", 2013, "Alfonso Cuarón", "English"),
            new MovieRow("Roma", 2018, "Alfonso Cuarón", "Spanish"),
            // Guillermo del Toro
            new MovieRow("Pan's Labyrinth", 2006, "Guillermo del Toro", "Spanish"),
            new MovieRow("The Shape of Water", 2017, "Guillermo del Toro", "English"),
            new MovieRow("Nightmare Alley", 2021, "Guillermo del Toro", "English"),
            new MovieRow("Cronos", 1993, "Guillermo del Toro", "Spanish"),
            // Alejandro G. Iñárritu
            new MovieRow("Amores perros", 2000, "Alejandro G. Iñárritu", "Spanish"),
            new MovieRow("21 Grams", 2003, "Alejandro G. Iñárritu", "English"),
            new MovieRow("Babel", 2006, "Alejandro G. Iñárritu", "English"),
            new MovieRow("Birdman", 2014, "Alejandro G. Iñárritu", "English"),
            new MovieRow("The Revenant", 2015, "Alejandro G. Iñárritu", "English"),
            // Spike Lee
            new MovieRow("Do the Right Thing", 1989, "Spike Lee", "English"),
            new MovieRow("Malcolm X", 1992, "Spike Lee", "English"),
            new MovieRow("25th Hour", 2002, "Spike Lee", "English"),
            new MovieRow("BlacKkKlansman", 2018, "Spike Lee", "English"),
            // Wes Anderson
            new MovieRow("Rushmore", 1998, "Wes Anderson", "English"),
            new MovieRow("The Royal Tenenbaums", 2001, "Wes Anderson", "English"),
            new MovieRow("The Grand Budapest Hotel", 2014, "Wes Anderson", "English"),
            new MovieRow("Moonrise Kingdom", 2012, "Wes Anderson", "English"),
            new MovieRow("The French Dispatch", 2021, "Wes Anderson", "English"),
            // Coen Brothers
            new MovieRow("Fargo", 1996, "Coen Brothers", "English"),
            new MovieRow("The Big Lebowski", 1998, "Coen Brothers", "English"),
            new MovieRow("No Country for Old Men", 2007, "Coen Brothers", "English"),
            new MovieRow("True Grit", 2010, "Coen Brothers", "English"),
            new MovieRow("O Brother, Where Art Thou?", 2000, "Coen Brothers", "English"),
            // Danny Boyle
            new MovieRow("Trainspotting", 1996, "Danny Boyle", "English"),
            new MovieRow("28 Days Later", 2002, "Danny Boyle", "English"),
            new MovieRow("Slumdog Millionaire", 2008, "Danny Boyle", "English"),
            new MovieRow("127 Hours", 2010, "Danny Boyle", "English"),
            // Darren Aronofsky
            new MovieRow("Requiem for a Dream", 2000, "Darren Aronofsky", "English"),
            new MovieRow("The Wrestler", 2008, "Darren Aronofsky", "English"),
            new MovieRow("Black Swan", 2010, "Darren Aronofsky", "English"),
            new MovieRow("The Whale", 2022, "Darren Aronofsky", "English"),
            // Paul Thomas Anderson
            new MovieRow("Boogie Nights", 1997, "Paul Thomas Anderson", "English"),
            new MovieRow("There Will Be Blood", 2007, "Paul Thomas Anderson", "English"),
            new MovieRow("The Master", 2012, "Paul Thomas Anderson", "English"),
            new MovieRow("Phantom Thread", 2017, "Paul Thomas Anderson", "English"),
            // Stanley Kubrick
            new MovieRow("2001: A Space Odyssey", 1968, "Stanley Kubrick", "English"),
            new MovieRow("A Clockwork Orange", 1971, "Stanley Kubrick", "English"),
            new MovieRow("The Shining", 1980, "Stanley Kubrick", "English"),
            new MovieRow("Full Metal Jacket", 1987, "Stanley Kubrick", "English"),
            new MovieRow("Paths of Glory", 1957, "Stanley Kubrick", "English"),
            // Alfred Hitchcock
            new MovieRow("Psycho", 1960, "Alfred Hitchcock", "English"),
            new MovieRow("Vertigo", 1958, "Alfred Hitchcock", "English"),
            new MovieRow("North by Northwest", 1959, "Alfred Hitchcock", "English"),
            new MovieRow("Rear Window", 1954, "Alfred Hitchcock", "English"),
            new MovieRow("Dial M for Murder", 1954, "Alfred Hitchcock", "English"),
            // Billy Wilder
            new MovieRow("Some Like It Hot", 1959, "Billy Wilder", "English"),
            new MovieRow("Sunset Boulevard", 1950, "Billy Wilder", "English"),
            new MovieRow("The Apartment", 1960, "Billy Wilder", "English"),
            new MovieRow("Double Indemnity", 1944, "Billy Wilder", "English"),
            // Akira Kurosawa
            new MovieRow("Seven Samurai", 1954, "Akira Kurosawa", "Japanese"),
            new MovieRow("Rashomon", 1950, "Akira Kurosawa", "Japanese"),
            new MovieRow("Yojimbo", 1961, "Akira Kurosawa", "Japanese"),
            new MovieRow("Ran", 1985, "Akira Kurosawa", "Japanese"),
            new MovieRow("Ikiru", 1952, "Akira Kurosawa", "Japanese"),
            // Federico Fellini
            new MovieRow("La Dolce Vita", 1960, "Federico Fellini", "Italian"),
            new MovieRow("8½", 1963, "Federico Fellini", "Italian"),
            new MovieRow("Nights of Cabiria", 1957, "Federico Fellini", "Italian"),
            new MovieRow("Amarcord", 1973, "Federico Fellini", "Italian"),
            // Ingmar Bergman
            new MovieRow("The Seventh Seal", 1957, "Ingmar Bergman", "Swedish"),
            new MovieRow("Persona", 1966, "Ingmar Bergman", "Swedish"),
            new MovieRow("Wild Strawberries", 1957, "Ingmar Bergman", "Swedish"),
            new MovieRow("Fanny and Alexander", 1982, "Ingmar Bergman", "Swedish"),
            // Andrei Tarkovsky
            new MovieRow("Stalker", 1979, "Andrei Tarkovsky", "Russian"),
            new MovieRow("Solaris", 1972, "Andrei Tarkovsky", "Russian"),
            new MovieRow("Andrei Rublev", 1966, "Andrei Tarkovsky", "Russian"),
            new MovieRow("The Mirror", 1975, "Andrei Tarkovsky", "Russian"),
            // Sergio Leone
            new MovieRow("The Good, the Bad and the Ugly", 1966, "Sergio Leone", "Italian"),
            new MovieRow("Once Upon a Time in the West", 1968, "Sergio Leone", "Italian"),
            new MovieRow("Once Upon a Time in America", 1984, "Sergio Leone", "English"),
            new MovieRow("A Fistful of Dollars", 1964, "Sergio Leone", "Italian"),
            // John Ford
            new MovieRow("The Searchers", 1956, "John Ford", "English"),
            new MovieRow("Stagecoach", 1939, "John Ford", "English"),
            new MovieRow("The Grapes of Wrath", 1940, "John Ford", "English"),
            new MovieRow("How Green Was My Valley", 1941, "John Ford", "English"),
            // Howard Hawks
            new MovieRow("His Girl Friday", 1940, "Howard Hawks", "English"),
            new MovieRow("The Big Sleep", 1946, "Howard Hawks", "English"),
            new MovieRow("Rio Bravo", 1959, "Howard Hawks", "English"),
            new MovieRow("Bringing Up Baby", 1938, "Howard Hawks", "English"),
            // Orson Welles
            new MovieRow("Citizen Kane", 1941, "Orson Welles", "English"),
            new MovieRow("Touch of Evil", 1958, "Orson Welles", "English"),
            new MovieRow("The Magnificent Ambersons", 1942, "Orson Welles", "English"),
            new MovieRow("Chimes at Midnight", 1965, "Orson Welles", "English"),
            // David Lynch
            new MovieRow("Eraserhead", 1977, "David Lynch", "English"),
            new MovieRow("Blue Velvet", 1986, "David Lynch", "English"),
            new MovieRow("Mulholland Drive", 2001, "David Lynch", "English"),
            new MovieRow("Twin Peaks: Fire Walk with Me", 1992, "David Lynch", "English"),
            // Terrence Malick
            new MovieRow("Badlands", 1973, "Terrence Malick", "English"),
            new MovieRow("Days of Heaven", 1978, "Terrence Malick", "English"),
            new MovieRow("The Thin Red Line", 1998, "Terrence Malick", "English"),
            new MovieRow("The Tree of Life", 2011, "Terrence Malick", "English"),
            // Michael Haneke
            new MovieRow("Funny Games", 1997, "Michael Haneke", "German"),
            new MovieRow("The White Ribbon", 2009, "Michael Haneke", "German"),
            new MovieRow("Amour", 2012, "Michael Haneke", "French"),
            new MovieRow("Caché", 2005, "Michael Haneke", "French"),
            // Lars von Trier
            new MovieRow("Breaking the Waves", 1996, "Lars von Trier", "English"),
            new MovieRow("Dancer in the Dark", 2000, "Lars von Trier", "English"),
            new MovieRow("Dogville", 2003, "Lars von Trier", "English"),
            new MovieRow("Melancholia", 2011, "Lars von Trier", "English"),
            // Wong Kar-wai
            new MovieRow("Chungking Express", 1994, "Wong Kar-wai", "Cantonese"),
            new MovieRow("In the Mood for Love", 2000, "Wong Kar-wai", "Cantonese"),
            new MovieRow("2046", 2004, "Wong Kar-wai", "Cantonese"),
            new MovieRow("Happy Together", 1997, "Wong Kar-wai", "Cantonese"),
            // Zhang Yimou
            new MovieRow("Raise the Red Lantern", 1991, "Zhang Yimou", "Mandarin"),
            new MovieRow("Hero", 2002, "Zhang Yimou", "Mandarin"),
            new MovieRow("House of Flying Daggers", 2004, "Zhang Yimou", "Mandarin"),
            new MovieRow("To Live", 1994, "Zhang Yimou", "Mandarin"),
            // Ang Lee
            new MovieRow("Crouching Tiger, Hidden Dragon", 2000, "Ang Lee", "Mandarin"),
            new MovieRow("Brokeback Mountain", 2005, "Ang Lee", "English"),
            new MovieRow("Life of Pi", 2012, "Ang Lee", "English"),
            new MovieRow("Eat Drink Man Woman", 1994, "Ang Lee", "Mandarin"),
            // John Woo
            new MovieRow("The Killer", 1989, "John Woo", "Cantonese"),
            new MovieRow("Hard Boiled", 1992, "John Woo", "Cantonese"),
            new MovieRow("Face/Off", 1997, "John Woo", "English"),
            new MovieRow("Mission: Impossible II", 2000, "John Woo", "English"),
            // Satyajit Ray
            new MovieRow("Pather Panchali", 1955, "Satyajit Ray", "Bengali"),
            new MovieRow("Aparajito", 1956, "Satyajit Ray", "Bengali"),
            new MovieRow("The World of Apu", 1959, "Satyajit Ray", "Bengali"),
            new MovieRow("Charulata", 1964, "Satyajit Ray", "Bengali"),
            // Yasujirō Ozu
            new MovieRow("Tokyo Story", 1953, "Yasujirō Ozu", "Japanese"),
            new MovieRow("Late Spring", 1949, "Yasujirō Ozu", "Japanese"),
            new MovieRow("Early Summer", 1951, "Yasujirō Ozu", "Japanese"),
            new MovieRow("Floating Weeds", 1959, "Yasujirō Ozu", "Japanese"),
            // Kenji Mizoguchi
            new MovieRow("Ugetsu", 1953, "Kenji Mizoguchi", "Japanese"),
            new MovieRow("Sansho the Bailiff", 1954, "Kenji Mizoguchi", "Japanese"),
            new MovieRow("The Life of Oharu", 1952, "Kenji Mizoguchi", "Japanese"),
            new MovieRow("Street of Shame", 1956, "Kenji Mizoguchi", "Japanese"),
            // Jean-Luc Godard
            new MovieRow("Breathless", 1960, "Jean-Luc Godard", "French"),
            new MovieRow("Contempt", 1963, "Jean-Luc Godard", "French"),
            new MovieRow("Pierrot le Fou", 1965, "Jean-Luc Godard", "French"),
            new MovieRow("Vivre sa vie", 1962, "Jean-Luc Godard", "French"),
            // François Truffaut
            new MovieRow("The 400 Blows", 1959, "François Truffaut", "French"),
            new MovieRow("Jules and Jim", 1962, "François Truffaut", "French"),
            new MovieRow("Day for Night", 1973, "François Truffaut", "French"),
            new MovieRow("The Last Metro", 1980, "François Truffaut", "French"),
            // Louis Malle
            new MovieRow("Elevator to the Gallows", 1958, "Louis Malle", "French"),
            new MovieRow("Murmur of the Heart", 1971, "Louis Malle", "French"),
            new MovieRow("Au revoir les enfants", 1987, "Louis Malle", "French"),
            new MovieRow("Atlantic City", 1980, "Louis Malle", "English"),
            // Jacques Tati
            new MovieRow("Jour de fête", 1949, "Jacques Tati", "French"),
            new MovieRow("Mr. Hulot's Holiday", 1953, "Jacques Tati", "French"),
            new MovieRow("Mon Oncle", 1958, "Jacques Tati", "French"),
            new MovieRow("PlayTime", 1967, "Jacques Tati", "French"),
            // Jean Renoir
            new MovieRow("The Rules of the Game", 1939, "Jean Renoir", "French"),
            new MovieRow("Grand Illusion", 1937, "Jean Renoir", "French"),
            new MovieRow("The River", 1951, "Jean Renoir", "English"),
            new MovieRow("La Bête humaine", 1938, "Jean Renoir", "French"),
            // Fritz Lang
            new MovieRow("Metropolis", 1927, "Fritz Lang", "German"),
            new MovieRow("M", 1931, "Fritz Lang", "German"),
            new MovieRow("The Big Heat", 1953, "Fritz Lang", "English"),
            new MovieRow("Dr. Mabuse the Gambler", 1922, "Fritz Lang", "German"),
            // F.W. Murnau
            new MovieRow("Nosferatu", 1922, "F.W. Murnau", "German"),
            new MovieRow("Sunrise: A Song of Two Humans", 1927, "F.W. Murnau", "English"),
            new MovieRow("The Last Laugh", 1924, "F.W. Murnau", "German"),
            new MovieRow("Faust", 1926, "F.W. Murnau", "German"),
            // Charlie Chaplin
            new MovieRow("The Kid", 1921, "Charlie Chaplin", "English"),
            new MovieRow("City Lights", 1931, "Charlie Chaplin", "English"),
            new MovieRow("Modern Times", 1936, "Charlie Chaplin", "English"),
            new MovieRow("The Great Dictator", 1940, "Charlie Chaplin", "English"),
            new MovieRow("Limelight", 1952, "Charlie Chaplin", "English"),
            // Buster Keaton
            new MovieRow("The General", 1926, "Buster Keaton", "English"),
            new MovieRow("Sherlock Jr.", 1924, "Buster Keaton", "English"),
            new MovieRow("Steamboat Bill, Jr.", 1928, "Buster Keaton", "English"),
            new MovieRow("Our Hospitality", 1923, "Buster Keaton", "English"),
            new MovieRow("The Navigator", 1924, "Buster Keaton", "English")
    );

    public record MovieRow(String title, int releaseYear, String directorName, String language) {
    }
}
