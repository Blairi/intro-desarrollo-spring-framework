package mx.unam.dgtic;

import mx.unam.dgtic.entity.Producto;
import mx.unam.dgtic.service.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class PracticaunoApplication implements CommandLineRunner {

    @Autowired
    private ProductoServicio productoServicio;

    public static void main(String[] args) {
        SpringApplication.run(PracticaunoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear
        Producto producto1=productoServicio.crearProducto("Laptop", new BigDecimal("15999.99"), 10, "laptop.jpg");
        Producto producto2=productoServicio.crearProducto("Mouse", new BigDecimal("299.50"), 50, "mouse.jpg");
        Producto producto3=productoServicio.crearProducto("Teclado", new BigDecimal("450.00"), 30, "teclado.jpg");

        System.out.println(producto1);

        // Eliminar por id
        productoServicio.borrar(producto2);

        // Eliminar por nombre
        productoServicio.borrarPorNombre("Teclado");

        System.out.println("----------------------");
        System.out.println(productoServicio.buscarId(producto1.getIdProducto()));

        // Volver a crear para que existan varios registros
        producto2=productoServicio.crearProducto("Mouse", new BigDecimal("299.50"), 50, "mouse.jpg");
        producto3=productoServicio.crearProducto("Teclado", new BigDecimal("450.00"), 30, "teclado.jpg");

        System.out.println("----------------------");
        for(Producto p:productoServicio.buscarTodos()) {
            System.out.println(p);
        }

        // Actualizar (update)
        producto1.setPrecio(new BigDecimal("14999.99"));
        producto1.setStock(8);
        productoServicio.actualizarProducto(producto1);
        System.out.println("Producto actualizado = " + productoServicio.buscarId(producto1.getIdProducto()));

        // Búsqueda por nombre exacto
        System.out.println("Buscado por nombre = " + productoServicio.buscarPorNombre(producto1.getNombre()));

        // Búsqueda por contenido de nombre
        System.out.println(productoServicio.buscarPorContenidoNombre("Lap"));

        // Búsqueda por stock
        System.out.println(productoServicio.buscarConStockMenorA(40));

        // Búsqueda por criterios
        System.out.println(productoServicio.buscarPorCriterios(null, new BigDecimal("300"), new BigDecimal("20000")));
    }
}