package dgtic.core.config;

import dgtic.core.modelo.Estudiante;
import org.springframework.context.annotation.*;

@Configuration
@ImportResource(locations = {"classpath:bean-configuration.xml", "classpath:bean-servicios.xml"})
@ComponentScan(basePackages = {"dgtic.core"})
public class ConfiguracionServicio {

    @Bean(name = "estudiante")
    @Scope("prototype")
    public Estudiante servicioEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Pedro");
        estudiante.setEdad(20);
        return estudiante;
    }
}
