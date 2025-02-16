import java.awt.Color;

public class FiguraGeometrica {
    private Color color = Color.BLACK;
    private double perimetro;
    private double area;

    public FiguraGeometrica(double perimetro, double area) {
        this.perimetro = perimetro;
        this.area = area;
    }
    
    public FiguraGeometrica(double perimetro, double area, Color color) {
        this.perimetro = perimetro;
        this.area = area;
        this.color = color;
    }
    
    public FiguraGeometrica(double perimetro, double area, int r, int g, int b){
        this.perimetro = perimetro;
        this.area = area;
        this.color = new Color(r,g,b);
    }

    public FiguraGeometrica(){}
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(double perimetro) {
        this.perimetro = perimetro;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}