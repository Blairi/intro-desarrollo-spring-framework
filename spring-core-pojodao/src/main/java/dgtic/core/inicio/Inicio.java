package dgtic.core.inicio;

import dgtic.core.modelo.Materia;
import dgtic.core.repositorio.impl.BaseDeDatosDAOImpl;
import dgtic.core.repositorio.intf.BaseDeDatosDAO;
import dgtic.core.servicio.ServicioDAO;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Inicio {
    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext("dgtic.core");

        BaseDeDatosDAO serv = context.getBean("baseDeDatosDAO", BaseDeDatosDAO.class);
        System.out.println(serv.getEstudiantes("ico"));

        System.out.println("--- Obtener estudiantes de IME");
        System.out.println(serv.getEstudiante("ime", "126"));
        System.out.println("Materias de " + serv.getEstudiante("ime", "126").getNombre());

        for (Materia m: serv.getEstudiante("ime", "126").getMaterias()) {
            System.out.println(m);
        }

        System.out.println("--- obtener CSV de IME ---");
        ServicioDAO csv = context.getBean(ServicioDAO.class);
        System.out.println(csv.archivoCSV("ime"));
        System.out.println("---------------------------");
    }
}
