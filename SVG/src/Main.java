public class Main {

    public static Segment findLongestSegment(Segment[] segments) {
        Segment longest = segments[0];
        for (Segment segment : segments) {
            if (segment.length() > longest.length()) {
                longest = segment;
            }
        }
        return longest;
    }


    public static void main(String[] args) {
        System.out.println("YES");
        Point punkt =new Point(2.3,4.5);

        System.out.println(punkt);
        System.out.println(punkt.toSvg());
        punkt.translate(2.3,4.5);
        System.out.println(punkt.toSvg());
        Point punkt2=punkt.translated(14,20);
        System.out.println(punkt2.toSvg());

        Segment segment=new Segment(punkt,punkt2);

        System.out.println(segment.length());

        punkt2.translate(2,4);
        System.out.println(punkt2.toSvg());
        System.out.println(segment.length());

        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(1, 1);
        Point p4 = new Point(4, 5);

        Segment[] segments = {
                new Segment(p1, p2),
                new Segment(p3, p4)
        };

        Segment longestSegment = findLongestSegment(segments);
        System.out.println("longest segment equal: " + longestSegment.length());



    }

}
