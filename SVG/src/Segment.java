public class Segment {

    public Point punkt1;
    public Point punkt2;
    public Point start;
    public Point end;


    public Segment(Point punkt1, Point punkt2) {
        this.punkt1 = punkt1;
        this.punkt2 = punkt2;
    }

//    public Segment(Point punkt1, Point punkt2) {
//        start = new Point(punkt1.x, punkt1.y);      //zrobiona kopia zeby nie mozna bylo zepsuc
//        end = new Point(punkt2.x, punkt2.y);        //to mi wogole nie dziala xD
//    }


    public double length() {
        double dx = punkt2.x - punkt1.x;
        double dy = punkt2.y - punkt1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
