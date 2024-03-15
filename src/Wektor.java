public class Wektor {
    double x1;

    public Wektor() {
    }

    public Wektor(double x1, double x2, double x3, double x4, String etykieta, double numerEtykiety) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.etykieta = etykieta;
        this.numerEtykiety = numerEtykiety;
    }

    @Override
    public String toString() {
        return "[" +
                x1 + ',' +
                x2 + ',' +
                x3 + ',' +
                x4 + ',' +
                " " + etykieta +"]";
    };


    double x2;
    double x3;
    double x4;
    String etykieta;
    double numerEtykiety;
}
