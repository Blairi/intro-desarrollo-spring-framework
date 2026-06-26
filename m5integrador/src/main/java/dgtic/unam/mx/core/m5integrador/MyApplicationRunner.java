package dgtic.unam.mx.core.m5integrador;

import dgtic.unam.mx.core.m5integrador.modelo.Empleado;
import dgtic.unam.mx.core.m5integrador.servicio.interfaces.ServicioEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyApplicationRunner implements CommandLineRunner {

    @Autowired
    private Empleado empleado;

    @Autowired
    private ServicioEmpleado servicioEmpleado;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hola mundo Spring Boot");
        empleado.trabajar();
        System.out.println(empleado);
        System.out.println("-----------------------");
        servicioEmpleado.ejecutarJornada();
    }
}
