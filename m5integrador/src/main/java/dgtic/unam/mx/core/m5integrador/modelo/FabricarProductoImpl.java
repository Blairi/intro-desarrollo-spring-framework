package dgtic.unam.mx.core.m5integrador.modelo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FabricarProductoImpl implements Actividad{

    @Override
    public void realizar() {
        System.out.println("Fabricando productos...");
    }
}
