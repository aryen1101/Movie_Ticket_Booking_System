package strategy.rules;

public class CapacitySurgeRule implements PricingRule {

    private final double threshold;
    private final double multiplier;

    public CapacitySurgeRule(double threshold, double multiplier) {
        this.threshold = threshold;
        this.multiplier = multiplier;
    }

    @Override
    public double apply(PricingContext context, double currentPrice) {
        double fillPercentage = (double) context.getShow().getBookedSeatsCount() / 
                                 context.getShow().getTotalCapacity();

        if (fillPercentage >= threshold) {
            return currentPrice * multiplier;
        }
        return currentPrice;
    }


    
}
