package mx.unam.dgtic;

import mx.unam.dgtic.entity.Usuario;
import mx.unam.dgtic.service.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class EntityManagerApplication implements CommandLineRunner {

    @Autowired
    private UsuarioServicio usuarioServicio;

	public static void main(String[] args) {
		SpringApplication.run(EntityManagerApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Usuario usuario1=usuarioServicio.crearUsuario("manuel","manuel@manuel.com");
        Usuario usuario2=usuarioServicio.crearUsuario("manuel2","manue1l@manuel.com");
        Usuario usuario3=usuarioServicio.crearUsuario("manuel3","manue2l@manuel.com");
        System.out.println(usuario1);
        usuarioServicio.borrar(usuario2);
        usuarioServicio.correoBorrar(usuario3);
        System.out.println("----------------------");
        System.out.println(usuarioServicio.buscarId((long)1));

        usuario2=usuarioServicio.crearUsuario("manuel2","manue1l@manuel.com");
        usuario3=usuarioServicio.crearUsuario("manuel3","manue2l@manuel.com");
        System.out.println("----------------------");
        for(Usuario us:usuarioServicio.buscarTodos()) {
            System.out.println(us);
        }

        System.out.println("usuarioBuscado = " + usuarioServicio.buscarPorUsername(usuario1.getUsername()));

        System.out.println(usuarioServicio.buscarPorContenidoCorreo(usuario1.getEmail()));

        System.out.println(usuarioServicio.crearUsuario(usuario1.getUsername(), usuario1.getEmail()));
    }
}
