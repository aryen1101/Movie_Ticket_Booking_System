package strategy.rules;

import enums.SeatType;
import models.MovieShow;

public class PricingContext {

    private final MovieShow show;
    private final SeatType seatType;
    private final double basePrice;

    public PricingContext(MovieShow show, SeatType seatType, double basePrice) {
        this.show = show;
        this.seatType = seatType;
        this.basePrice = basePrice;
    }

    public MovieShow getShow() {
        return show;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public double getBasePrice() {
        return basePrice;
    }

}
