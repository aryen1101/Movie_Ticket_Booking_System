package strategy;

import enums.SeatType;
import models.MovieShow;

public interface PricingStrategy {

    public double calculatePrice(SeatType seatType, double basePrice, MovieShow movieShow);

}
