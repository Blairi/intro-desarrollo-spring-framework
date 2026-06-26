package dgtic.unam.mx.core.m5demoweb.controlador;

import dgtic.unam.mx.core.m5demoweb.servicio.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal")
public class ControladorPrincipal {

    @Autowired
    private AutomovilService automovilService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("auto1", automovilService.obtenerAutomovil());
        return "index";
    }

}
