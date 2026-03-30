package models;

import enums.SeatType;

public class Seat {

    private final String id;
    private final String rowLabel;
    private final int seatNumber;
    private final SeatType seatType;

    public Seat(String id, String rowLabel, int seatNumber, SeatType seatType) {
        this.id = id;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public String getId() {
        return id;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

}
