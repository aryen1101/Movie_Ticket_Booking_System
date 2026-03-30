package strategy.rules;

public interface  PricingRule {

    double apply(PricingContext context, double currentPrice);
    
}
