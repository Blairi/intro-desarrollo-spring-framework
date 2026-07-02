package mx.unam.dgtic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(length = 255)
    private String nombre;

    @Column(precision = 38, scale = 2)
    private BigDecimal precio;

    @Column
    private Integer stock;

    @Column(length = 255)
    private String imagen;
}