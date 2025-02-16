public class Mate {
    public static final double PI = 3.141592653589793;

    public static double pow(double numero) {
        return numero * numero;
    }

    public static double sqrt(double numero) {
        return Math.sqrt(numero);
    }

    public static double toRadians(double grados) {
        return grados * PI / 180.0;
    }

    public static double cos(double anguloEnGrados) {
        double anguloEnRadianes = toRadians(anguloEnGrados);
        return calcularCos(anguloEnRadianes);
    }

    public static double sin(double anguloEnGrados) {
        double anguloEnRadianes = toRadians(anguloEnGrados);
        return calcularSin(anguloEnRadianes);
    }

    public static double min(double a, double b) {
        return (a < b) ? a : b;
    }

    public static double max(double a, double b) {
        return (a > b) ? a : b;
    }

    private static double calcularCos(double anguloEnRadianes) {
        double coseno = 1;
        double termino = 1;
        double potencia = 1;
        int signo = -1;
        
        for (int i = 2; i <= 10; i += 2) {
            potencia *= anguloEnRadianes * anguloEnRadianes;
            termino = potencia / factorial(i);
            coseno += signo * termino;
            signo *= -1;
        }
        return coseno;
    }

    private static double calcularSin(double anguloEnRadianes) {
        double seno = anguloEnRadianes;
        double termino = anguloEnRadianes;
        double potencia = anguloEnRadianes;
        int signo = -1;
        
        for (int i = 3; i <= 9; i += 2) {
            potencia *= anguloEnRadianes * anguloEnRadianes;
            termino = potencia / factorial(i);
            seno += signo * termino;
            signo *= -1;
        }
        return seno;
    }

    private static double factorial(int n) {
        double resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
}
