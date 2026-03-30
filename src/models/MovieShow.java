package models;

import enums.ShowStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import strategy.PricingStrategy;

public class MovieShow {

    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private ShowStatus status;
    private final double basePrice;
    private final PricingStrategy strategy;

    private final Set<String> bookedSeats = new HashSet<>();
    private final Map<String, SeatLock> lockedSeats = new HashMap<>();

    public MovieShow(String id, Movie movie, Screen screen, LocalDateTime startTime,
            double basePrice, PricingStrategy strategy) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movie.getDurationMins()).plusMinutes(30);

        this.basePrice = basePrice;
        this.strategy = strategy;
        this.status = ShowStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ShowStatus getStatus() {
        return status;
    }

    public void setStatus(ShowStatus status) {
        this.status = status;
    }

    public int getTotalCapacity() {
        return screen.getCapacity();
    }

    public synchronized int getBookedSeatsCount() {
        return bookedSeats.size();
    }

    public synchronized double calculateTotal(List<Seat> seats) {
        double total = 0;
        for (Seat s : seats) {
            total += strategy.calculatePrice(s.getSeatType(), basePrice, this);
        }
        return total;
    }

    public synchronized boolean lockSeats(List<String> seatIds, String userId) {
        for (String seatId : seatIds) {
            if (bookedSeats.contains(seatId)) {
                return false;
            }
            SeatLock lock = lockedSeats.get(seatId);
            if (lock != null && !lock.isExpired() && !lock.getUserId().equals(userId)) {
                return false;
            }
        }
        for (String seatId : seatIds) {
            lockedSeats.put(seatId, new SeatLock(userId));
        }
        return true;
    }

    public synchronized void confirmBooking(List<String> seatIds, String userId) {
        for (String seatId : seatIds) {
            SeatLock lock = lockedSeats.get(seatId);
            if (lock == null || !lock.getUserId().equals(userId)) {
                throw new IllegalStateException("Invalid Lock");
            }
            bookedSeats.add(seatId);
            lockedSeats.remove(seatId);
        }
    }

    public synchronized void releaseLocks(List<String> ids, String userId) {
        for (String seatId : ids) {
            SeatLock lock = lockedSeats.get(seatId);
            if (lock != null && lock.getUserId().equals(userId)) {
                lockedSeats.remove(seatId);
            }
        }
    }

    public synchronized void releaseBookedSeats(List<String> ids) {
        bookedSeats.removeAll(ids);
    }
}
