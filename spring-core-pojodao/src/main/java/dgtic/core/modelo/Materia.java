package dgtic.core.modelo;

public class Materia {
    private String nombre;
    private Integer creditos;

    public Materia(String nombre, Integer credits) {
        this.nombre = nombre;
        this.creditos = credits;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "nombre='" + nombre + '\'' +
                ", credits=" + creditos +
                '}';
    }
}
