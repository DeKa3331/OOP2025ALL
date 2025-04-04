public class Polygon {
    private final Point[] vertices;
    private Style style;

    // Konstruktor dokonuje głębokiej kopii tablicy
    public Polygon(Point[] vertices, Style style) {
        this.vertices = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            this.vertices[i] = new Point(vertices[i]);
        }
        this.style = style;
    }

    // Konstruktor kopiujący (głęboka kopia)
    public Polygon(Polygon other) {
        this.vertices = new Point[other.vertices.length];
        for (int i = 0; i < other.vertices.length; i++) {
            this.vertices[i] = new Point(other.vertices[i]);
        }
        this.style = new Style(other.style.getFill(), other.style.getStroke(), other.style.getStrokeWidth());
    }

    public void setPoint(int ix, int x, int y) {
        this.vertices[ix].setX(x);
        this.vertices[ix].setY(y);
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    public BoundingBox boundingBox() {
        if (vertices.length == 0) {
            return null;
        }
        double minX = vertices[0].getX();
        double maxX = vertices[0].getX();
        double minY = vertices[0].getY();
        double maxY = vertices[0].getY();
        for (int i = 1; i < vertices.length; i++) {
            if (vertices[i].getX() < minX) minX = vertices[i].getX();
            if (vertices[i].getX() > maxX) maxX = vertices[i].getX();
            if (vertices[i].getY() < minY) minY = vertices[i].getY();
            if (vertices[i].getY() > maxY) maxY = vertices[i].getY();
        }
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Point p : vertices) {
            s.append(p.getX()).append(",").append(p.getY()).append(" ");
        }
        return s.toString().trim();
    }

    public String toSvg() {
        return "<polygon points=\"" + this + "\" style=\"" + style.toString() + "\" />";
    }
}
