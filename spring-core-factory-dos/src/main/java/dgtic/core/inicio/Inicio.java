package dgtic.core.inicio;

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

            EmpleadoServicio servUno = (EmpleadoServicio) context.getBean("empleadoServicio");
            servUno.servicioEmpleado();

            ReporteEmpleadoServicio servDos = (ReporteEmpleadoServicio) context.getBean("empleadoServicioReporte");
            servDos.servicioEmpleado();

            System.out.println("Recuperando factory directamnete");
            PredetermindaoServicio servicioTres = (PredetermindaoServicio)context.getBean("servicioLocal");
            servicioTres.getReporteEmpleadoServicio().servicioEmpleado();
            servicioTres.getEmpleadoServicio().servicioEmpleado();

            ((ClassPathXmlApplicationContext) context).close();

        } catch (UnsatisfiedDependencyException e) {
            System.out.println("no se pudo cargar el bean");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
