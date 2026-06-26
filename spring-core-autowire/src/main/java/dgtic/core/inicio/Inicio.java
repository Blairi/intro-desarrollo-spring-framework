package dgtic.core.inicio;

import dgtic.core.modelo.Empleado;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;

public class Inicio {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{
                            "bean-configuration.xml"
                    }
            );

            System.out.println("------ Empleado uno --------");
            Empleado emp = (Empleado) context.getBean("empleado");
            System.out.println("emp = " + emp);

            if (emp.getActividad() != null) {
                emp.getActividad().realiza();
            }

            ((ClassPathXmlApplicationContext) context).close();

        } catch (UnsatisfiedDependencyException e) {
            System.out.println("no se pudo cargar el bean");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
