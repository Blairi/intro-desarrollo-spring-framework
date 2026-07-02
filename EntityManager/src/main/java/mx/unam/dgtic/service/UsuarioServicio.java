package mx.unam.dgtic.service;

import jakarta.persistence.TypedQuery;
import mx.unam.dgtic.entity.Usuario;
import mx.unam.dgtic.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(String username,String email){
        Usuario usuario=new Usuario(username,email);
        return usuarioRepository.guardar(usuario);
    }

    public void borrar(Usuario usuario){
        usuarioRepository.eliminar(usuario.getId());
    }

    public void correoBorrar(Usuario usuario){
        usuarioRepository.eliminarPorCorreo(usuario);
    }

    public Optional<Usuario> buscarId(Long id){
        return usuarioRepository.buscarPorId(id);
    }

    public List<Usuario> buscarTodos(){
        return usuarioRepository.buscarTodos();
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.buscarPorUsername(username);
    }

    public List<Usuario> buscarPorContenidoCorreo(String email) {
        return usuarioRepository.buscarPorContenidoCorreo(email);
    }

    public List<Usuario> buscarPorCiterios(String username,String email) {
        return usuarioRepository.buscarPorCiterios(username, email);
    }
}
