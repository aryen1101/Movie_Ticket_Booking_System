package strategy;

import enums.SeatType;
import java.util.List;
import models.MovieShow;
import strategy.rules.PricingContext;
import strategy.rules.PricingRule;

public class RuleBasedPricingStrategy implements PricingStrategy {

    private final List<PricingRule> rules;

    public RuleBasedPricingStrategy(List<PricingRule> rules) {
        this.rules = List.copyOf(rules);
    }

    @Override
    public double calculatePrice(SeatType seatType, double basePrice, MovieShow movieShow) {

        PricingContext context = new PricingContext(movieShow, seatType, basePrice);
        double finalPrice = basePrice;

        for (PricingRule rule : rules) {
            finalPrice = rule.apply(context, finalPrice);
        }

        return finalPrice;

    }

}
