package mx.unam.dgtic.servicio;

import mx.unam.dgtic.config.AppConfig;
import mx.unam.dgtic.dao.UsuarioDao;
import mx.unam.dgtic.modelo.Usuario;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ServicioUsuario {
    ApplicationContext context=null;
    UsuarioDao usuarioDao=null;

    public ServicioUsuario() {
        context=new AnnotationConfigApplicationContext(AppConfig.class);
        usuarioDao=context.getBean(UsuarioDao.class);
    }
    public Long guardar(Usuario usuario){
        Long filas=usuarioDao.guardar(usuario);
        return filas;
    }

    public int actualizar(Usuario usuario) {
        int filas=usuarioDao.actualizar(usuario);
        return filas;
    }

    public int eliminar(Long id){
        int filas=usuarioDao.eliminar(id);
        return filas;
    }

    public Usuario buscarId(Long id){
        Usuario usuario=usuarioDao.buscarPorId(id);
        return usuario;
    }

    public List<Usuario> todosUsuarios(){
        return usuarioDao.listarTodos();
    }
}
