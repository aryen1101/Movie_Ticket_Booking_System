import enums.*;
import java.time.LocalDateTime;
import java.util.*;
import models.*;
import service.MovieTicketBookingSystem;
import strategy.*;
import strategy.rules.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Theatre> allTheatres = new HashMap<>();
        Map<String, MovieShow> allShows = new HashMap<>();
        Map<String, MovieTicket> allTickets = new HashMap<>();

        MovieTicketBookingSystem bookingSystem = new MovieTicketBookingSystem(allTheatres, allShows, allTickets);

        City blr = new City("C1", "Bangalore");
        Theatre pvr = new Theatre("T1", "PVR Koramangala", blr);
        allTheatres.put(pvr.getId(), pvr);

        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat("S1", "A", 1, SeatType.REGULAR));
        seats.add(new Seat("S2", "P", 1, SeatType.PREMIUM));

        Screen screen1 = new Screen("SC1", "IMAX", pvr, seats);
        pvr.addScreen(screen1);

        Movie inception = new Movie("M1", "Inception", 150);

        Map<SeatType, Double> premiums = new HashMap<>();
        premiums.put(SeatType.PREMIUM, 50.0);
        PricingStrategy strategy = new RuleBasedPricingStrategy(Collections.singletonList(new SeatTypePremiumRule(premiums)));

        LocalDateTime time = LocalDateTime.now().plusHours(2);
        MovieShow show1 = new MovieShow("SH1", inception, screen1, time, 200.0, strategy);
        allShows.put(show1.getId(), show1);

        List<Theatre> theatres = bookingSystem.searchTheatreByCity("C1");
        if (!theatres.isEmpty()) {
            System.out.println("Theatres in Bangalore: " + theatres.get(0).getName());
        }

        List<Movie> movies = bookingSystem.searchMoviesByCity("C1");
        if (!movies.isEmpty()) {
            System.out.println("Movies in Bangalore: " + movies.get(0).getTitle());
        }

        List<Movie> theatreMovies = bookingSystem.searchMovieByTheatre("T1");
        if (!theatreMovies.isEmpty()) {
            System.out.println("Movies at PVR: " + theatreMovies.get(0).getTitle());
        }

        User user = new User("U1", "Aryen");
        try {
            MovieTicket ticket = bookingSystem.bookTicket("SH1", seats, user, PaymentMethod.UPI);
            System.out.println("Booking successful. Amount: " + ticket.getTotalAmount());

            bookingSystem.cancelTicket(ticket.getId());
            System.out.println("Ticket cancelled successfully");
        } catch (Exception e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }
}