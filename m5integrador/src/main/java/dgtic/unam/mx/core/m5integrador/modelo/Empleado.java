package dgtic.unam.mx.core.m5integrador.modelo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Empleado {
    @Value("${EMPLEADO_NOMBRE}")
    private String nombre;
    @Autowired
    @Qualifier("fabricarProductoImpl")
    private Actividad actividad;

    @Value("${EMPLEADO_DEPARTAMENTO}") //Notación EL - Expresive Language
    private String department;

    public Empleado() {
    }

    public Empleado(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public void trabajar() {
        actividad.realizar();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @PostConstruct
    public void registrarEntrada() {
        System.out.println("Registrando entrada...");
    }

    @PreDestroy
    public void registrandoSalida() {
        System.out.println("Registrando salida...");
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", actividad=" + actividad +
                ", department='" + department + '\'' +
                '}';
    }
}
