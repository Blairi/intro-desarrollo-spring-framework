package dgtic.core.modelo;

import dgtic.core.servicio.FabricaCoches;

public class Persona {
    private String nombre;
    private FabricaCoches fabrica;

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FabricaCoches getFabrica() {
        return fabrica;
    }

    public void setFabrica(FabricaCoches fabrica) {
        this.fabrica = fabrica;
    }

    public FabricaCoches getCoche() {
        return fabrica;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", fabrica=" + fabrica +
                '}';
    }
}
