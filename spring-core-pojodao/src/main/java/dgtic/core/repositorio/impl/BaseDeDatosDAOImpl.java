package dgtic.core.repositorio.impl;

import dgtic.core.modelo.Estudiante;
import dgtic.core.repositorio.BaseDeDatos;
import dgtic.core.repositorio.intf.BaseDeDatosDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("baseDeDatosDAO")
public class BaseDeDatosDAOImpl implements BaseDeDatosDAO {

    @Override
    public List<Estudiante> getEstudiantes(String carrera) {
        System.out.println("BD normal ...");
        return BaseDeDatos.carreras.get(carrera);
    }

    @Override
    public Estudiante getEstudiante(String carrera, String matricula) {
        return BaseDeDatos.carreras.get(carrera)
                .stream()
                .filter(estudiante -> estudiante.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }
}
