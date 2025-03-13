import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Style styl1 = new Style("red", "blue", 2.0);
        Style styl2 = new Style("yellow", "black", 3.0);
        Style styl3 = new Style("blue", "red", 4.0);

        Point p1 = new Point(30 ,40);
        Point p2 = new Point();
        p2.setY(40);
        System.out.println(p1);
        System.out.println(p2);

        Segment seg = new Segment(p1, p2);
        System.out.println(seg);
        p2.setX(60);
        System.out.println(seg);

        Point[] arr = {p1, p2, p1};
        arr[2] = new Point(30, 0);

        Polygon poly = new Polygon(arr, styl1);
        System.out.println(poly);

        Polygon polyCopy = new Polygon(poly);
        polyCopy.setStyle(styl2);
        System.out.println(polyCopy);

        poly.setPoint(1, 0, 40);
        System.out.println("Poly: "+poly);
        System.out.println("Kopia: "+polyCopy);

        System.out.println(polyCopy.toSvg());

        SvgScene scene = new SvgScene();
        scene.addPolygon(poly);
        scene.addPolygon(polyCopy);
        polyCopy.setPoint(0, -50, -50);
        polyCopy.setPoint(1, 0, 0);
        polyCopy.setPoint(2, -100, -30);

        Polygon square = new Polygon(new Point[]{
                new Point(70, 70),
                new Point(130, 70),
                new Point(130, 130),
                new Point(70, 130)
        }, styl3);
        scene.addPolygon(square);

        System.out.println("Scena svg:");
        System.out.println(scene.toSvg());

        System.out.println(polyCopy.boundingBox());

        System.out.println(scene.toSvg());

        scene.save("rysunek.svg");
    }
}
