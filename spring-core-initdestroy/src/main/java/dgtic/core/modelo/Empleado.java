package dgtic.core.modelo;

public class Empleado {
    private String nombre;
    private Integer edad;

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

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                '}';
    }

    public void iniciar() {
        System.out.println("Empleado inciado");
    }

    public void limpiar() {
        System.out.println("Empleado eliminado, liberando recursos");
    }
}
