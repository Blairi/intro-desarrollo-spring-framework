package mx.unam.dgtic;

import mx.unam.dgtic.modelo.Usuario;
import mx.unam.dgtic.servicio.ServicioUsuario;

public class Inicio {
    public static void main(String[] args) {
        ServicioUsuario servicioUsuario=new ServicioUsuario();
        Usuario usuario=new Usuario(null,"Mike","mike@gmail.com",50);
        Usuario usuario2=new Usuario(null,"Mike_2","mike2@gmail.com",12);
        Long id=servicioUsuario.guardar(usuario);
        servicioUsuario.guardar(usuario2);
        usuario.setId(id);
        System.out.println(usuario);
        usuario.setNombre("Marco");
        servicioUsuario.actualizar(usuario);
        System.out.println(usuario);

        //consulta
        System.out.println("------");
        for(Usuario us:servicioUsuario.todosUsuarios()){
            System.out.println(us);
        }
        //borrar
        servicioUsuario.eliminar(usuario.getId());
        System.out.println("------");
        for(Usuario us:servicioUsuario.todosUsuarios()){
            System.out.println(us);
        }
        // buscar usuario por id
        System.out.println("------");
        Usuario us = servicioUsuario.buscarId(2L);
        System.out.println("us = " + us);
    }
}