package backend.rafhergom.tfg.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Data
@Entity
@Table(name = "categorianegocio")
public class CategoriaNegocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_categoria", nullable = false, length = 50)
    private String nombreCategoria;

    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "imagenURL")
    private String imagenURL;
    
    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date fechaModificacion;

    @Column(name = "usuario_creacion")
    private Long usuarioCreacion;

    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;

    // Puedes agregar relaciones con otras entidades según tus necesidades, por ejemplo:
    // @OneToMany(mappedBy = "categoria")
    // private List<Producto> productos;
}