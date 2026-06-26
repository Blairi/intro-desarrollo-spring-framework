package dgtic.inicio;

import dgtic.modelo.Profesor;
import dgtic.modelo.Responsabilidades;

public class MetodoUno {
    public static void main(String[] args) {
        Profesor profesor = new Profesor();
        Responsabilidades resp = new Responsabilidades();
        //profesor.setResponsabilidad(resp);
        //profesor.setNombre("Juan");

        profesor.getResponsabilidad().explicarClase();
        profesor.getResponsabilidad().calificar();
        profesor.getResponsabilidad().generarReportes();
    }
}
