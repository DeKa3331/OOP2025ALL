public abstract class BeverageDecorator implements Beverage {
    private Beverage decoratedBeverage;

    public BeverageDecorator(Beverage decoratedBeverage) {
//        if (decoratedBeverage==null)
//        {
//            throw new RuntimeException();
//        }
        this.decoratedBeverage = decoratedBeverage;
    }

    @Override
    public String serve() {
        if(decoratedBeverage==null)
        {
            return "puste, nie dziala";
        }
        return decoratedBeverage.serve();
    }
}
