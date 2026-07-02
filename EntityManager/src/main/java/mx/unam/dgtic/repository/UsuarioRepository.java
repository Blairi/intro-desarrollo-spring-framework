package mx.unam.dgtic.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import mx.unam.dgtic.entity.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Usuario guardar(Usuario usuario){
        if(usuario.getId()==null){
            entityManager.persist(usuario);
            return usuario;
        }else{
            return entityManager.merge(usuario);
        }
    }

    @Transactional
    public void eliminar(Long id){
        Usuario usuario=entityManager.find(Usuario.class,id);
        if(usuario!=null){
            entityManager.remove(usuario);
        }
    }

    @Transactional
    public void eliminarPorCorreo(Usuario usuario){
        Query query=entityManager.createQuery("DELETE FROM Usuario u WHERE u.email=:email");
        query.setParameter("email",usuario.getEmail());
        query.executeUpdate();
    }

    public Optional<Usuario> buscarPorId(Long id){
        Usuario usuario=entityManager.find(Usuario.class,id);
        return Optional.ofNullable(usuario);
    }

    public List<Usuario> buscarTodos(){
        TypedQuery<Usuario> query=entityManager.createQuery("SELECT u FROM Usuario u",Usuario.class);
        return query.getResultList();
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery(
                    "SELECT u FROM Usuario u WHERE u.username=:username", Usuario.class
            );
            query.setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Usuario> buscarPorContenidoCorreo(String email){
        TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT u FROM Usuario u WHERE u.email LIKE :email", Usuario.class);
        query.setParameter("email","%"+email+"%");
        return query.getResultList();
    }

    public List<Usuario> buscarPorCiterios(String username,String email){
        var cb=entityManager.getCriteriaBuilder();
        var query=cb.createQuery(Usuario.class);
        var root=query.from(Usuario.class);
        var predicate=cb.conjunction();

        if(username!=null && !username.isEmpty()){
            predicate=cb.and(predicate,
                    cb.like(root.get("username"),"%"+username+"%"));
        }
        if(email!=null && !email.isEmpty()){
            predicate=cb.and(predicate,
                    cb.like(root.get("email"),"%"+email+"%"));
        }
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

}