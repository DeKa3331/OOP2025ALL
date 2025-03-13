public final class Style {
    public final String fillColor;
    public final String strokeColor;
    public final Double strokeWidth;

    public Style(String fillColor, String strokeColor, Double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }




    public String toSvg() {
        return "fill:" + fillColor + ";stroke:" + strokeColor + ";stroke-width:" + strokeWidth;
        //TODO dodac wersje magistra
    }
}