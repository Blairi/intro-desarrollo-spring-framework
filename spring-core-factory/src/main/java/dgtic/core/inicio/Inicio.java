package dgtic.core.inicio;

import dgtic.core.modelo.Persona;
import dgtic.core.modelo.TiposCarro;
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

            System.out.println("----- Persona Uno --------");
            Persona persona = (Persona) context.getBean("persona");
            System.out.println("persona = " + persona);
            persona.getFabrica().getModeloCoche(TiposCarro.FAMILIAR).crear();

            System.out.println("----- Persona Dos --------");
            Persona personaDos = (Persona) context.getBean("persona");
            personaDos.setNombre("Diego");
            System.out.println("personaDos = " + personaDos);
            System.out.println("persona = " + persona);
            persona.getFabrica().getModeloCoche(TiposCarro.DEPORTIVO).crear();

            ((ClassPathXmlApplicationContext) context).close();

        } catch (UnsatisfiedDependencyException e) {
            System.out.println("no se pudo cargar el bean");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
