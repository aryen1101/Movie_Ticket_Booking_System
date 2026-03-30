package models;

import enums.PaymentStatus;
import enums.TicketStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MovieTicket {
    private final String id;
    private final MovieShow show;
    private  final List<Seat> seats;
    private final User user;
    private final double totalAmount;
    private Payment payment;
    private TicketStatus status;
    public MovieTicket(MovieShow show, List<Seat> seats, User user, double totalAmount) {
        this.id = UUID.randomUUID().toString();
        this.show = show;
        this.seats = new ArrayList<>(seats);
        this.user = user;
        this.totalAmount = totalAmount;
        this.status = TicketStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public MovieShow getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public User getUser() {
        return user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void confirm(Payment payment) { 
        this.payment = payment; 
        this.status = TicketStatus.CONFIRMED; 
    }
    
    public void cancel() {
        this.status = TicketStatus.CANCELLED;
        if (this.payment != null) {
            this.payment.setStatus(PaymentStatus.REFUNDED);
        }
    }

    public void printTicket() {
        System.out.println("--------------------------------------------------");
        System.out.println("TICKET " + status + " | ID: " + id);
        System.out.println("Movie: " + show.getMovie().getTitle() + " | Screen: " + show.getScreen().getName());
        System.out.println("Time: " + show.getStartTime().toLocalTime());
        System.out.println("Customer: " + user.getName());
        
        String seatString = seats.stream()
            .map(s -> s.getRowLabel() + s.getSeatNumber())
            .collect(Collectors.joining(", "));
            
        System.out.println("Seats: " + seatString);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("--------------------------------------------------");
    }

    
    
}
