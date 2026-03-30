package strategy.rules;

import enums.SeatType;
import java.util.Map;

public class SeatTypePremiumRule implements PricingRule {

    private final Map<SeatType , Double> premiums;

    public SeatTypePremiumRule(Map<SeatType, Double> premiums) {
        this.premiums = Map.copyOf(premiums);
    }

    @Override
    public double apply(PricingContext context, double currentPrice) {
        return currentPrice + premiums.getOrDefault(context.getSeatType(), 0.0);
    }

    
}
