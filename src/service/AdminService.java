package service;

import enums.ShowStatus;
import java.util.Map;
import models.Movie;
import models.MovieShow;
import models.Theatre;

public class AdminService {

    private final Map<String, Movie> movies;
    private final Map<String, Theatre> theatres;
    private final Map<String, MovieShow> shows;

    public AdminService(Map<String, Movie> movies, Map<String, Theatre> theatres, Map<String, MovieShow> shows) {
        this.movies = movies;
        this.theatres = theatres;
        this.shows = shows;
    }

    public void addMovie(Movie movie) {
        movies.put(movie.getId(), movie);
    }

    public void addTheatre(Theatre theatre) {
        theatres.put(theatre.getId(), theatre);
    }

    public void addMovieShow(MovieShow show) {
        for (MovieShow existingShow : shows.values()) {
            if (existingShow.getScreen().getId().equals(show.getScreen().getId()) && existingShow.getStatus() != ShowStatus.CANCELLED) {
                if (show.getStartTime().isBefore(existingShow.getEndTime()) && show.getEndTime().isAfter(existingShow.getStartTime())) {
                    throw new IllegalStateException("Screen Clash! Screen " + show.getScreen().getName() + " is occupied by " + existingShow.getMovie().getTitle());
                }

            }
        }
        shows.put(show.getId(), show);
        System.out.println("ADMIN Scheduled " + show.getMovie().getTitle());
    }

}
