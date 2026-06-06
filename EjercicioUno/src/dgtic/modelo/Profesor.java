package dgtic.modelo;

public class Profesor {
    private String nombre;
    private Responsabilidades responsabilidad;

    public Profesor() {
        responsabilidad = new Responsabilidades();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Responsabilidades getResponsabilidad() {
        return responsabilidad;
    }

    public void setResponsabilidad(Responsabilidades responsabilidad) {
        this.responsabilidad = responsabilidad;
    }
}
