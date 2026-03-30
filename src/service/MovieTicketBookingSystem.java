package service;

import enums.PaymentMethod;
import enums.TicketStatus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Movie;
import models.MovieShow;
import models.MovieTicket;
import models.Payment;
import models.Seat;
import models.Theatre;
import models.User;

public class MovieTicketBookingSystem {

    private final Map<String, Theatre> theatres;
    private final Map<String, MovieShow> shows;
    private final Map<String, MovieTicket> tickets;

    public MovieTicketBookingSystem(Map<String, Theatre> theatresDB, Map<String, MovieShow> showsDB, Map<String, MovieTicket> ticketsDB) {
        this.theatres = theatresDB;
        this.shows = showsDB;
        this.tickets = ticketsDB;
    }

    public List<Movie> searchMoviesByCity(String cityId) {
        Set<Movie> uniqueMovies = new HashSet<>();
        
        for (MovieShow show : shows.values()) {
            String showCityId = show.getScreen().getTheatre().getCity().getCityId();
            
            if (showCityId.equals(cityId)) {
                uniqueMovies.add(show.getMovie());
            }
        }
        return new ArrayList<>(uniqueMovies);
    }

    public List<Theatre> searchTheatreByCity(String cityId) {
        List<Theatre> result = new ArrayList<>();
        
        for (Theatre theatre : theatres.values()) {
            if (theatre.getCity().getCityId().equals(cityId)) {
                result.add(theatre);
            }
        }
        return result;
    }

    public List<Movie> searchMovieByTheatre(String theatreId) {
        Set<Movie> uniqueMovies = new HashSet<>();
        
        for (MovieShow show : shows.values()) {
            String currentTheatreId = show.getScreen().getTheatre().getId();
            
            if (currentTheatreId.equals(theatreId)) {
                uniqueMovies.add(show.getMovie());
            }
        }
        return new ArrayList<>(uniqueMovies);
    }

    public List<Seat> getAvailableSeats(String showId) {
        MovieShow show = shows.get(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }
        return show.getScreen().getSeats();
    }

    public MovieTicket bookTicket(String showId, List<Seat> selectedSeats, User user, PaymentMethod method) {
        MovieShow show = shows.get(showId);
        
        List<String> seatIds = new ArrayList<>();
        for (Seat seat : selectedSeats) {
            seatIds.add(seat.getId());
        }

        if (!show.lockSeats(seatIds, user.getId())) {
            throw new RuntimeException("Booking failed: Some seats are not available.");
        }

        try {
            double totalAmount = show.calculateTotal(selectedSeats);
            
            show.confirmBooking(seatIds, user.getId());

            MovieTicket ticket = new MovieTicket(show, selectedSeats, user, totalAmount);
            ticket.confirm(new Payment(totalAmount, method));
            
            tickets.put(ticket.getId(), ticket);
            return ticket;

        } catch (Exception e) {
            show.releaseLocks(seatIds, user.getId());
            throw e;
        }
    }

    public void cancelTicket(String ticketId) {
        MovieTicket ticket = tickets.get(ticketId);
        
        if (ticket == null || ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new IllegalArgumentException("Invalid or already cancelled ticket.");
        }

        List<String> seatIds = new ArrayList<>();
        for (Seat seat : ticket.getSeats()) {
            seatIds.add(seat.getId());
        }
        
        ticket.getShow().releaseBookedSeats(seatIds);
        ticket.cancel();
    }
    
}
