package backend.rafhergom.tfg.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String nombreProducto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "stock_disponible", nullable = false)
    private int stockDisponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
