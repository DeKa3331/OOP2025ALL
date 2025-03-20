public abstract class Shape {
    //protected pozwala na dostep z klas dziedziczacych
    //private tylko z danej klasy
    protected Style style;

    public Shape(Style style) {
        this.style = style;
    }

    public abstract BoundingBox boundingBox();

    public abstract String toSvg();

}
