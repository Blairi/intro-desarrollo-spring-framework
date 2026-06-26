package dgtic.core.servicio;

public class PredetermindaoServicio {
    private static EmpleadoServicio servicioEmpleado
            = new EmpleadoServicioImpl();
    private static ReporteEmpleadoServicio empleadoReporteServicio
            = new ReporteEmpleadoServicioImpl();

    public PredetermindaoServicio() {
    }

    public EmpleadoServicio getEmpleadoServicio() {
        return servicioEmpleado;
    }

    public ReporteEmpleadoServicio getReporteEmpleadoServicio() {
        return empleadoReporteServicio;
    }
}
