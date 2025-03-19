public class Text extends Shape {
    private final String text;
    private final Point position;
    private final int textLength;
    private final int fontSize;

    public Text(String text, Point position, int fontSize, int textLength, Style style) {
        super(style);
        this.text = text;
        this.position = position;
        this.fontSize = fontSize;
        this.textLength = textLength;
    }

    @Override
    public String toSvg() {
        return "<text x=\""+position.getX()+"\" y=\""+position.getY()+"\" textLength=\""+textLength+"\" font-size=\""+fontSize+"\" style=\""+style.toSvg()+"\">"+text+"</text>";
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(position.getX(), position.getY(), textLength, fontSize);
    }
}
