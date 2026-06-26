package dgtic.inicio;

import dgtic.modelo.*;

public class MetodoTres {
    public static void main(String[] args) {
        Profesor profesor = new Profesor();
        profesor.setNombre("Pedro");
        profesor.getResponsabilidades().add(new ExplicarClase());
        profesor.getResponsabilidades().add(new Calificar());
        profesor.getResponsabilidades().add(new Reportes());

        for (Responsabilidades resp : profesor.getResponsabilidades()) {
            System.out.println("El profesor " + profesor.getNombre() + " ");
            resp.realizar();
        }
    }
}
