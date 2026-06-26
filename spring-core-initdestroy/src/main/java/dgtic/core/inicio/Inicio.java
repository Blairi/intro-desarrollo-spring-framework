package dgtic.core.inicio;

import dgtic.core.modelo.Empleado;
import dgtic.core.modelo.Persona;
import dgtic.core.servicio.Servicio;
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

            Empleado emp = context.getBean(Empleado.class);
            System.out.println("---- Iniciar Servicio ----");
            Servicio serv = context.getBean(Servicio.class);
            System.out.println("---- Limpiando empleado ----");
            emp.limpiar();


            ((ClassPathXmlApplicationContext) context).close();

        } catch (UnsatisfiedDependencyException e) {
            System.out.println("no se pudo cargar el bean");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
