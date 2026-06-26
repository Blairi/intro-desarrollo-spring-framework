package dgtic.unam.mx.core.m5integrador.modelo;

import org.springframework.stereotype.Component;

@Component
public class SupervisarPersonalImpl implements Actividad{

    @Override
    public void realizar() {
        System.out.println("Supervisando al personal...");
    }
}
