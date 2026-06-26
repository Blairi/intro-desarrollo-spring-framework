package dgtic.unam.mx.core.m5demoweb.servicio;

import dgtic.unam.mx.core.m5demoweb.modelo.Automovil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomovilServiceImpl implements AutomovilService{

    @Autowired
    private Automovil automovil;

    @Override
    public Automovil obtenerAutomovil() {
        automovil.setMarca("Toyota");
        automovil.setModelo("Corolla");
        automovil.setFoto("https://m.media-amazon.com/images/I/71NGoxuAcaL._AC_UL320_.jpg");
        return automovil;
    }

    @Override
    public Automovil obtenerOtroAutomovil() {
        automovil.setMarca("Mazda");
        automovil.setModelo("3");
        automovil.setFoto("https://m.media-amazon.com/images/I/61yBE+gQWaL._AC_UL320_.jpg");
        return automovil;
    }
}
