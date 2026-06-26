package dgtic.core.inicio;

import dgtic.core.modelo.Empleado;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Inicio {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{
                        "bean-configuration.xml"
                }
        );
        Empleado emp = (Empleado) context.getBean("empleado");
        System.out.println("emp = " + emp);

        Empleado empDos = (Empleado) context.getBean("empleadoDos");
        System.out.println("empDos = " + empDos);

        if (empDos.getActividad() != null) {
            empDos.getActividad().realiza();
        }

        System.out.println("------- Empleado tres ----------");
        Empleado emptTres = (Empleado) context.getBean("empleadoTres");
        System.out.println("emptTres = " + emptTres);
        if (emptTres.getActividad() != null) {
            emptTres.getActividad().realiza();
        }

        System.out.println("------ Empleado cuatro --------");
        Empleado empCuatro = (Empleado) context.getBean("empleadoCuatro");
        System.out.println("empCuatro = " + empCuatro);
        if (emptTres.getActividad() != null) {
            empCuatro.getActividad().realiza();
        }

        System.out.println("--------- Empleado Cinco ---------");
        Empleado empCinco = (Empleado) context.getBean("empleadoCinco");
        System.out.println("empCinco = " + empCinco);
        if (empCinco.getActividad() != null) {
            empCinco.getActividad().realiza();
        }

        ((ClassPathXmlApplicationContext) context).close();
    }
}
