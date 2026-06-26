package dgtic.core.modelo;

import java.util.Collection;

public class Empleado {
    private String nombre;
    private Integer edad;
    private Collection<Actividades> actividad;

    public Empleado() {
    }

    public Empleado(Integer edad) {
        this.edad = edad;
    }

    public Empleado(String nombre) {
        this.nombre = nombre;
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


    public Collection<Actividades> getActividad() {
        return actividad;
    }

    public void setActividad(Collection<Actividades> actividad) {
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
