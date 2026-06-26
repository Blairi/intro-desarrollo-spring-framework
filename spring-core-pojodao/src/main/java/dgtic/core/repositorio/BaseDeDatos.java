package dgtic.core.repositorio;

import dgtic.core.modelo.Estudiante;
import dgtic.core.modelo.Materia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDeDatos {
    public static Map<String, List<Estudiante>> carreras = new HashMap<>();

    static {
        // Estudiantes de ing. en computacion
        List<Estudiante> estudiantes = new ArrayList<>();
        Estudiante estudiante = new Estudiante("Rosa", "123", 20);
        ArrayList<Materia> materias = new ArrayList<>();
        materias.add(new Materia("Calculo", 9));
        materias.add(new Materia("Programacion", 10));
        materias.add(new Materia("Logica", 19));
        estudiante.setMaterias(materias);
        estudiantes.add(estudiante);
        carreras.put("ico", estudiantes);

        // Estudiantes de Ing. Mecanica Electrica
        estudiantes = new ArrayList<>();
        estudiante = new Estudiante("125", "Mario", 20);
        materias = new ArrayList<>();
        materias.add(new Materia("Calculo", 9));
        materias.add(new Materia("Circuitos Logicos", 10));
        materias.add(new Materia("Automatas", 10));
        estudiante.setMaterias(materias);

        estudiante = new Estudiante("126", "Esmeralda", 22);
        materias = new ArrayList<>();
        materias.add(new Materia("Circuitos Logicos", 10));
        materias.add(new Materia("Automatas", 10));
        estudiante.setMaterias(materias);
        estudiantes.add(estudiante);
        carreras.put("ime", estudiantes);


    }
}
