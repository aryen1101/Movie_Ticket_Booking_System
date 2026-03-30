package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen {

    private final String id;
    private final String name;
    private final Theatre theatre;
    private final List<Seat> seats;

    public Screen(String id, String name, Theatre theatre, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.theatre = theatre;
        this.seats = new ArrayList<>(seats);
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public int getCapacity() {
        return seats.size();
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

}
