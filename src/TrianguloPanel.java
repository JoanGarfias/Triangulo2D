import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.geom.Point2D;

public class TrianguloPanel extends javax.swing.JPanel { 
    private int numPoints;
    private int pointIndex = -1;
    private static final int POINT_WIDTH = 10;
    private boolean puntosEditables = false;
    private boolean lineasEditables = false;
    private int anguloDeRotacion = 90;
    
    Triangulo triangulo = new Triangulo();
    
        public class Punto{
            int x,y;
            public Punto() {}
            public Punto(int x, int y) {
                this.x = x;
                this.y = y;
            }
            public int getX() {
                return x;
            }
            public void setX(int x) {
                this.x = x;
            }
            public int getY() {
                return y;
            }
            public void setY(int y) {
                this.y = y;
            }
            public double distance(Punto p) {
                int deltaX = x - p.getX();
                int deltaY = y - p.getY();
                return Mate.sqrt(deltaX * deltaX + deltaY * deltaY);
            }
            public double distance(int x2, int y2){
                int deltaX = x - x2;
                int deltaY = y - y2;
                return Mate.sqrt(deltaX * deltaX + deltaY * deltaY);
            }
            public void setCoordenadas(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    
    Punto p1,p2,p3;
        
    public void calcularElementos(){
        
        //System.out.println("Elementos calculados");
        double perimetro = 0.0;
        double semi = 0.0;
        double area = 0.0;
        double altura = 0.0;
        double a = p1.distance(p2);
        double b = p2.distance(p3);
        double c = p3.distance(p1);
        
        triangulo.setLados(a, b, c);
        
        perimetro += a;
        perimetro += b;
        perimetro += c;
        
        triangulo.setPerimetro(perimetro);
        semi = perimetro/2;
        
        area = Mate.sqrt(semi * ( (semi-a) * (semi-b) * (semi-c) ) );
        triangulo.setArea(area);
        
        if(a == b && b == c && c==a){ //Equilatero
            triangulo.setAltura(a / 2.0 * Mate.sqrt(3) );
        }
        else{
                //System.out.println("Encontre un triangulo ISOSCELES!");
                double base = (a != b)? a : (b != c) ? b : c;
                //System.out.print("la base es " + base);
                //System.out.print("el area es " + area);
                triangulo.setAltura(2.0 * area / base );
        }
        
    }
    
    public TrianguloPanel() {
        initComponents();
        p1 = new Punto();
        p2 = new Punto();
        p3 = new Punto();
        numPoints = 3;
        p1.setCoordenadas(50, 50);
        p2.setCoordenadas(150,50);
        p3.setCoordenadas(100, 150);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (puntosEditables) {
                    //System.out.println("x= " + e.getX() + "y= " + e.getY());
                    Punto[] puntos = {p1, p2, p3};
                    for (int i = 0; i < puntos.length; i++) {
                        if (puntos[i].distance(e.getX(), e.getY()) <= POINT_WIDTH) {
                            pointIndex = i;
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                pointIndex = -1;
            }
        });
        
        
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                calcularElementos();
                if (puntosEditables && pointIndex != -1) {
                    int newX = e.getX();
                    int newY = e.getY();

                    // Verificar si las coordenadas sobrepasan los bordes del JPanel
                    if (newX < 0 || newX > getWidth() || newY < 0 || newY > getHeight()) {
                        try {
                            throw new TrianguloDesbordadoException("Triángulo desbordado: coordenadas fuera de los límites del panel");
                        } catch (TrianguloDesbordadoException ex) {
                           // ex.printStackTrace(); // Maneja la excepción de la manera que desees
                            System.out.println("ERROR: Limites del panel alcanzados");
                        }
                        return; // Salir del método si las coordenadas están fuera de los límites
                    }
                    

                    Punto[] puntos = {p1, p2, p3};
                    puntos[pointIndex].setCoordenadas(newX, newY);

                    calcularElementos();

                    repaint();
                }
            }
        });

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(triangulo.getColor());

        int[] xPoints = {(int) p1.getX(), (int) p2.getX(), (int) p3.getX()};
        int[] yPoints = {(int) p1.getY(), (int) p2.getY(), (int) p3.getY()};

        g.drawPolygon(xPoints, yPoints, xPoints.length);

        for (int i = 0; i < xPoints.length; i++) {
            g.fillOval(xPoints[i] - POINT_WIDTH / 2, yPoints[i] - POINT_WIDTH / 2, POINT_WIDTH, POINT_WIDTH);
        }
        
    }

    public void setPuntosEditables(boolean value) {
        this.puntosEditables = value;
    }
    public void setLineasEditables(boolean value) {
        this.lineasEditables = value;
    }

    public boolean getPuntosEditables(){
        return puntosEditables;
    }
    public boolean getLineasEditables(){
        return lineasEditables;
    }

    public int getAnguloDeRotacion() {
        return anguloDeRotacion;
    }

    public void setAnguloDeRotacion(int anguloDeRotacion) {
        this.anguloDeRotacion = anguloDeRotacion;
    }

    
    
    public void changeTriangleColor() {
        Random rand = new Random();
        triangulo.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        repaint();
    }
    
    public void repintar(){
        repaint();
    }

    public void rotarTriangulo() throws TrianguloDesbordadoException {
        // Calcular el punto medio del triángulo
        double centerX = (p1.getX() + p2.getX() + p3.getX()) / 3.0;
        double centerY = (p1.getY() + p2.getY() + p3.getY()) / 3.0;
        Punto centro = new Punto((int) centerX, (int)centerY);

        double angulo = Math.toRadians(anguloDeRotacion); // Ángulo de rotación en radianes
        double cos = Math.cos(angulo);
        double sin = Math.sin(angulo);

        // Calcular las coordenadas relativas de cada vértice respecto al punto medio
        p1.setX(p1.getX() - centro.getX());
        p1.setY(p1.getY() - centro.getY());
        p2.setX(p2.getX() - centro.getX());
        p2.setY(p2.getY() - centro.getY());
        p3.setX(p3.getX() - centro.getX());
        p3.setY(p3.getY() - centro.getY());

        // Aplicar la rotación a cada vértice
        rotarPunto(p1, cos, sin);
        rotarPunto(p2, cos, sin);
        rotarPunto(p3, cos, sin);

        // Sumar las coordenadas relativas rotadas al punto medio para obtener las nuevas coordenadas
        p1.setX(p1.getX() + centro.getX());
        p1.setY(p1.getY() + centro.getY());
        p2.setX(p2.getX() + centro.getX());
        p2.setY(p2.getY() + centro.getY());
        p3.setX(p3.getX() + centro.getX());
        p3.setY(p3.getY() + centro.getY());

        System.out.println("x1 = " + p1.getX() + " y1= " + p1.getY());
        System.out.println("x2 = " + p2.getX() + " y2= " + p2.getY());
        System.out.println("x3 = " + p3.getX() + " y3= " + p3.getY());

        Punto[] puntos = {p1, p2, p3};
        rearmarPuntos(puntos, this.getWidth(), this.getHeight());
        
        repaint();
    }

    private void rearmarPuntos(Punto[] puntos, int panelWidth, int panelHeight) {
        for (Punto punto : puntos) {
            int x = punto.getX();
            int y = punto.getY();

            // Ajustar las coordenadas si se salen de los límites del panel
            if (x < 0) {
                x = 0; // Si X es negativo, establecerlo en 0
            } else if (x > panelWidth) {
                x = panelWidth; // Si X es mayor que el ancho del panel, establecerlo como el ancho del panel
            }
            if (y < 0) {
                y = 0; // Si Y es negativo, establecerlo en 0
            } else if (y > panelHeight) {
                y = panelHeight; // Si Y es mayor que la altura del panel, establecerlo como la altura del panel
            }

            // Asignar las coordenadas ajustadas al punto
            punto.setCoordenadas(x, y);
        }
    }


    private void rotarPunto(Punto punto, double cos, double sin) throws TrianguloDesbordadoException {
        int tempX = punto.getX();
        int tempY = punto.getY();

        int PANEL_WIDTH = this.getWidth();
        int PANEL_HEIGHT = this.getHeight();

        // Calcular las coordenadas rotadas
        int tempRotatedX = (int) (cos * tempX - sin * tempY);
        int tempRotatedY = (int) (sin * tempX + cos * tempY);
        punto.setCoordenadas(tempRotatedX, tempRotatedY);
    }


    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            
            JFrame frame = new JFrame("Triángulo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TrianguloPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
        });
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 326, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
