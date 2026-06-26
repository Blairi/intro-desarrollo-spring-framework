package dgtic.core.servicio;

import dgtic.core.repositorio.intf.BaseDeDatosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ServicioDAO {

    /*
    @Autowired
    @Qualifier("baseDeDatosDAOExtra")
    private BaseDeDatosDAO servicioDAO;
     */

    private BaseDeDatosDAO servicioDAO;
    @Autowired
    public void setServicioDAO(@Qualifier("baseDeDatosDAOExtra") BaseDeDatosDAO baseDeDatosDAO) {
        this.servicioDAO = baseDeDatosDAO;
    }

    public BaseDeDatosDAO getServicioDAO() {
        return servicioDAO;
    }

    public String archivoCSV(String carrera) {
        return servicioDAO.getEstudiantes(carrera).stream()
                .map(alm->alm.getMatricula()+";"+
                        (alm.getMaterias().stream()
                                .map(mat->(mat.getNombre()+";"+mat.getCreditos()))
                                .collect(Collectors.joining(";")))+";"+alm.getNombre())
                .collect(Collectors.joining("\n"));
    }

}
