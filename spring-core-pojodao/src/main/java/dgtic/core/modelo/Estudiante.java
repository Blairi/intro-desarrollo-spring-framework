package dgtic.core.modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String nombre;
    private String matricula;
    private List<Materia> materias = new ArrayList<>();
    private Integer edad;

    public Estudiante(String matricula, String nombre, Integer edad) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", matricula='" + matricula + '\'' +
                ", materias=" + materias +
                ", edad=" + edad +
                '}';
    }
}
