package dgtic.core.inicio;

import dgtic.core.modelo.Actividades;
import dgtic.core.modelo.Empleado;
import dgtic.core.modelo.EmpleadoM;
import dgtic.core.modelo.EmpleadoP;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;

public class Inicio {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{
                        "bean-configuration.xml"
                }
        );

        System.out.println("------ Empleado uno --------");
        Empleado emp = (Empleado) context.getBean("empleado");
        System.out.println("emp = " + emp);
        for (Actividades actividad : emp.getActividad()) {
            actividad.realiza();
        }

        System.out.println("------ Empleado dos --------");
        Empleado empDos = (Empleado) context.getBean("empleadoDos");
        System.out.println("empDos = " + empDos);
        for (Actividades actividad : empDos.getActividad()) {
            actividad.realiza();
        }

        System.out.println("------ Empleado tres --------");
        EmpleadoM empTres = (EmpleadoM) context.getBean("empleadoTres");
        System.out.println("empTres = " + empTres);
        for (Actividades actividad : empTres.getActividad().values()) {
            actividad.realiza();
        }

        System.out.println("------ Empleado cuatro --------");
        EmpleadoP empCuatro = (EmpleadoP) context.getBean("empleadoCuatro");
        System.out.println("empCuatro = " + empCuatro);
        for (Iterator<Object> iter = empCuatro.getActividad().keySet().iterator(); iter.hasNext();) {
            String llave = (String) iter.next();
            System.out.println("llave = " + llave);
            System.out.println(empCuatro.getActividad().get(llave));
        }

        ((ClassPathXmlApplicationContext) context).close();
    }
}
