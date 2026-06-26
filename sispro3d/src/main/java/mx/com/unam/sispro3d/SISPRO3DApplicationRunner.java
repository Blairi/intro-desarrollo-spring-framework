package mx.com.unam.sispro3d;

import mx.com.unam.sispro3d.service.SISPRO3DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SISPRO3DApplicationRunner implements CommandLineRunner {

    @Autowired
    private SISPRO3DService sispro3DService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========== SISPRO3D - Módulo 5 ==========");
        sispro3DService.mostrarCategory();
        System.out.println();
        sispro3DService.mostrarAccount();
        System.out.println();
        sispro3DService.mostrarService();
        System.out.println();
        sispro3DService.mostrarReview();
        System.out.println("==========================================");
    }
}