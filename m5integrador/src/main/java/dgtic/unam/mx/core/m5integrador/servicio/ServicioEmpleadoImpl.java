package dgtic.unam.mx.core.m5integrador.servicio;

import dgtic.unam.mx.core.m5integrador.modelo.Empleado;
import dgtic.unam.mx.core.m5integrador.servicio.interfaces.ServicioEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioEmpleadoImpl implements ServicioEmpleado {
    @Autowired
    private Empleado empleado;

    public ServicioEmpleadoImpl(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public void ejecutarJornada() {
        System.out.println("--- Aqui va la lógica de negocio ---");
        empleado.registrarEntrada();
        empleado.trabajar();
        empleado.registrandoSalida();
        System.out.println("--- Fin de la ejecucion de la lógica de negocio ---");
    }
}
