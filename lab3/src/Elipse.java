public class Elipse extends Shape{
    private Point center;
    private Double rX;
    private Double rY;//radiusy

    public Elipse(Style style, Point center, Double rX, Double rY) {
        super(style);
        this.center = center;
        this.rX = rX;
        this.rY = rY;
    }

    @Override
    public String toString() {
        return "Elipse{" +
                "center=" + center +
                ", rX=" + rX +
                ", rY=" + rY +
                '}';
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(center.getX()-rX, center.getY()-rY,2*rX,2*rY);
    }

    @Override
    public String toSvg() {

return   "<ellipse cx=\""+center.getX()+"\"cy=\""+center.getY()+ "\"rx=\" "+rX+
                "\" ry=\""+rY+"\" fill=\""+style.toSvg()+"\" />";
    }
}
