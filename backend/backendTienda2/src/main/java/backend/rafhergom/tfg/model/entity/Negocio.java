package backend.rafhergom.tfg.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import backend.rafhergom.tfg.model.dtos.CategoriaNegocioDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
@Entity
@Table(name = "Negocio")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_negocio_id", nullable = false)
    private CategoriaNegocio categoriaNegocio;

    @Column(name = "nombre", nullable = false , length = 100)
    private String nombre;
    
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "imagenURL", nullable = false)
    private String imagenURL;
}
