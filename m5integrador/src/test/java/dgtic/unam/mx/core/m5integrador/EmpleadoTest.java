package dgtic.unam.mx.core.m5integrador;

import dgtic.unam.mx.core.m5integrador.modelo.Actividad;
import dgtic.unam.mx.core.m5integrador.modelo.Empleado;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmpleadoTest {

    @Autowired
    @Qualifier("supervisarPersonalImpl")
    private Actividad supervisarPersonalImpl;

    @Autowired
    private Empleado empleado;

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Configuracion previa");
    }

    @AfterAll
    public static void ejecucionPosterior() {
        System.out.println("Configuracion despues");
    }

    @Test
    public void test() {
        System.out.println("Hola mundo Test");
        assertNotNull(supervisarPersonalImpl);
        assertTrue(supervisarPersonalImpl instanceof Actividad);
    }

    @Test
    public void test2() {
        String esperado = "Juan";
        String resultado = empleado.getNombre();
        assertEquals(esperado, resultado);
    }

    @Test
    public void test3() {
        String esperado = "Desarrollo Sistemas";
        String resultado = empleado.getDepartment();
        assertEquals(esperado, resultado);
    }
}
