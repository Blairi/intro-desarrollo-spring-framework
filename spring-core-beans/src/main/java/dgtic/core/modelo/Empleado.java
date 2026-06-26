package dgtic.core.modelo;

public class Empleado {
    private String nombre;
    private Integer edad;
    private Actividades actividad;

    public Empleado() {
    }

    public Empleado(Integer edad) {
        this.edad = edad;
    }

    public Empleado(Actividades actividad) {
        this.actividad = actividad;
    }

    public Empleado(String nombre, Integer edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Actividades getActividad() {
        return actividad;
    }

    public void setActividad(Actividades actividad) {
        this.actividad = actividad;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", actividad=" + actividad +
                '}';
    }
}
