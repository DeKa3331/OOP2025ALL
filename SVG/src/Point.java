class Point {
    public double x;
    public  double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }





    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
//    public String toSvg() {
//        return "<svg height=\"100\" width=\"100\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
//                "  <circle r=\"45\" cx=\"" + x + "\" cy=\"" + y + "\" fill=\"red\" stroke=\"green\" stroke-width=\"3\" />\n" +
//                "</svg>";
//    }

    public String toSvg() {
        return String.format("""
                <svg height="100" width="100" xmlns="http://www.w3.org/2000/svg">
                  <circle r="45" cx="%.2f" cy="%.2f" fill="red" stroke="green" stroke-width="3" />
                </svg>""", this.x, this.x);
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public Point translated(double dx, double dy) {
       return new Point(this.x + dx, this.y + dy);
    }
}