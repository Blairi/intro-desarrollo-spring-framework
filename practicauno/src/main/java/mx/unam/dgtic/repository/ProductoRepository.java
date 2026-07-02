package mx.unam.dgtic.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import mx.unam.dgtic.entity.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Producto guardar(Producto producto){
        if(producto.getIdProducto()==null){
            entityManager.persist(producto);
            return producto;
        }else{
            return entityManager.merge(producto);
        }
    }

    @Transactional
    public void eliminar(Long id){
        Producto producto=entityManager.find(Producto.class,id);
        if(producto!=null){
            entityManager.remove(producto);
        }
    }

    @Transactional
    public void eliminarPorNombre(String nombre){
        Query query=entityManager.createQuery("DELETE FROM Producto p WHERE p.nombre=:nombre");
        query.setParameter("nombre",nombre);
        query.executeUpdate();
    }

    public Optional<Producto> buscarPorId(Long id){
        Producto producto=entityManager.find(Producto.class,id);
        return Optional.ofNullable(producto);
    }

    public List<Producto> buscarTodos(){
        TypedQuery<Producto> query=entityManager.createQuery("SELECT p FROM Producto p",Producto.class);
        return query.getResultList();
    }

    public Optional<Producto> buscarPorNombre(String nombre) {
        try {
            TypedQuery<Producto> query = entityManager.createQuery(
                    "SELECT p FROM Producto p WHERE p.nombre=:nombre", Producto.class
            );
            query.setParameter("nombre", nombre);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Producto> buscarPorContenidoNombre(String nombre){
        TypedQuery<Producto> query = entityManager.createQuery(
                "SELECT p FROM Producto p WHERE p.nombre LIKE :nombre", Producto.class);
        query.setParameter("nombre","%"+nombre+"%");
        return query.getResultList();
    }

    public List<Producto> buscarConStockMenorA(Integer stock){
        TypedQuery<Producto> query = entityManager.createQuery(
                "SELECT p FROM Producto p WHERE p.stock < :stock", Producto.class);
        query.setParameter("stock", stock);
        return query.getResultList();
    }

    public List<Producto> buscarPorCriterios(String nombre, BigDecimal precioMin, BigDecimal precioMax){
        var cb=entityManager.getCriteriaBuilder();
        var query=cb.createQuery(Producto.class);
        var root=query.from(Producto.class);
        var predicate=cb.conjunction();

        if(nombre!=null && !nombre.isEmpty()){
            predicate=cb.and(predicate,
                    cb.like(root.get("nombre"),"%"+nombre+"%"));
        }
        if(precioMin!=null){
            predicate=cb.and(predicate,
                    cb.greaterThanOrEqualTo(root.get("precio"),precioMin));
        }
        if(precioMax!=null){
            predicate=cb.and(predicate,
                    cb.lessThanOrEqualTo(root.get("precio"),precioMax));
        }
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
}