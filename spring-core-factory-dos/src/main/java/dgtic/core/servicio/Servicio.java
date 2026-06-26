package dgtic.core.servicio;

public class Servicio {
    public static Servicio servicio = new Servicio();

    public Servicio() {
    }

    public static Servicio getInstance() {
        return servicio;
    }
}
