package mx.unam.dgtic.service;

import mx.unam.dgtic.entity.Producto;
import mx.unam.dgtic.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto crearProducto(String nombre, BigDecimal precio, Integer stock, String imagen){
        Producto producto=new Producto(null, nombre, precio, stock, imagen);
        return productoRepository.guardar(producto);
    }

    public Producto actualizarProducto(Producto producto){
        return productoRepository.guardar(producto);
    }

    public void borrar(Producto producto){
        productoRepository.eliminar(producto.getIdProducto());
    }

    public void borrarPorNombre(String nombre){
        productoRepository.eliminarPorNombre(nombre);
    }

    public Optional<Producto> buscarId(Long id){
        return productoRepository.buscarPorId(id);
    }

    public List<Producto> buscarTodos(){
        return productoRepository.buscarTodos();
    }

    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre);
    }

    public List<Producto> buscarPorContenidoNombre(String nombre) {
        return productoRepository.buscarPorContenidoNombre(nombre);
    }

    public List<Producto> buscarConStockMenorA(Integer stock) {
        return productoRepository.buscarConStockMenorA(stock);
    }

    public List<Producto> buscarPorCriterios(String nombre, BigDecimal precioMin, BigDecimal precioMax) {
        return productoRepository.buscarPorCriterios(nombre, precioMin, precioMax);
    }
}