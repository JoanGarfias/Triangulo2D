
import java.awt.Color;

    public class Triangulo extends FiguraGeometrica{
        private double altura;
        private double lado1,lado2,lado3;
        
        public Triangulo(double perimetro, double area, double altura) {
            super(perimetro, area);
            this.altura = altura;
        }
        
        public Triangulo(double perimetro, double area) {
            super(perimetro, area);
        }
        
        public Triangulo(){}

        public void setLados(double a, double b, double c){
            this.lado1 = a;
            this.lado2 = b;
            this.lado3 = c;
        }
        
        public double []getLados(){
            double[] lados = {lado1, lado2, lado3};
            return lados;
        }

        public double getAltura() {
            return altura;
        }

        public void setAltura(double altura) {
            this.altura = altura;
        }
    }  