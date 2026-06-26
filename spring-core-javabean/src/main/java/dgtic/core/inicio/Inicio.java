package dgtic.core.inicio;

import dgtic.core.config.ConfiguracionServicio;
import dgtic.core.modelo.Estudiante;
import dgtic.core.modelo.Materia;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Inicio {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(ConfiguracionServicio.class);

        Estudiante estudiante =
                context.getBean("estudiante", Estudiante.class);

        System.out.println(estudiante);
        estudiante.setEdad(21);
        System.out.println(estudiante);

        Estudiante estudianteDos = context.getBean("estudiante", Estudiante.class);
        System.out.println(estudianteDos);
        estudianteDos.setNombre("Martha");
        System.out.println("--- imprimir ambos beans ---");
        System.out.println(estudiante);
        System.out.println(estudianteDos);
        System.out.println("cambio de nombres est uno");
        estudiante.setNombre("juan");
        System.out.println("--- imprimir ambos beans --");
        System.out.println(estudiante);
        System.out.println(estudianteDos);

        System.out.println("--- recuperando bean materia ---");
        Materia materia = context.getBean("materia", Materia.class);
        System.out.println("materia = " + materia);

        System.out.println("--- Estudiante Tres ---");
        Estudiante estudianteTres =
                context.getBean("estudianteTres", Estudiante.class);
        System.out.println("estudianteTres = " + estudianteTres);


        System.out.println("--- materia 2 ---");
        Materia materiaDos = context.getBean("materiaDos", Materia.class);
        System.out.println("materiaDos = " + materiaDos);

        context.close();
    }
}
