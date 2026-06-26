package dgtic.core.inicio;

import dgtic.core.modelo.Persona;
import dgtic.core.servicio.EmpleadoServicio;
import dgtic.core.servicio.PredetermindaoServicio;
import dgtic.core.servicio.ReporteEmpleadoServicio;
import dgtic.core.servicio.ReporteEmpleadoServicioImpl;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Inicio {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{
                            "bean-configuration.xml",
                            "bean-services.xml"
                    }
            );

            Persona personaUno = context.getBean(Persona.class);
            Persona personaDos = context.getBean(Persona.class);
            System.out.println("personaUno = " + personaUno);
            personaUno.setNombre("Otro nombre");
            System.out.println("personaUno = " + personaUno);
            System.out.println("personaDos = " + personaDos);

            ((ClassPathXmlApplicationContext) context).close();

        } catch (UnsatisfiedDependencyException e) {
            System.out.println("no se pudo cargar el bean");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
