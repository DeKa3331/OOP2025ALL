public class Style {
    private String fill;
    private String stroke;
    private int strokeWidth;

    public Style(String fill, String stroke, int strokeWidth) {
        this.fill = fill;
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    public String toString() {
        return "fill:" + fill + ";stroke:" + stroke + ";stroke-width:" + strokeWidth;
    }
}
