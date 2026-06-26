package dgtic.modelo;

import java.util.ArrayList;

public class Profesor {
    private String nombre;
    private ArrayList<Responsabilidades> responsabilidades;

    public Profesor() {
        this.responsabilidades = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Responsabilidades> getResponsabilidades() {
        return responsabilidades;
    }

    public void setResponsabilidades(ArrayList<Responsabilidades> responsabilidades) {
        this.responsabilidades = responsabilidades;
    }
}
