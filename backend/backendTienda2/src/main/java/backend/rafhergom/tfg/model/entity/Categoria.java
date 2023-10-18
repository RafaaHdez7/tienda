package backend.rafhergom.tfg.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_categoria", nullable = false, length = 50)
    private String nombreCategoria;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // Puedes agregar relaciones con otras entidades según tus necesidades, por ejemplo:
    // @OneToMany(mappedBy = "categoria")
    // private List<Producto> productos;
}
