package backend.rafhergom.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.HistoricoTransacciones;

@Repository
public interface HistoricoTransaccionesRepository extends JpaRepository<HistoricoTransacciones, Long> {
    // Otra consulta personalizada utilizando JPQL (Java Persistence Query Language)
    @Query("SELECT p FROM HistoricoTransacciones p WHERE p.usuario.id = ?1")
    List<HistoricoTransacciones> findHistoricoTransaccionesPorUsuarioId(Long usuarioId);
    
 // Otra consulta personalizada utilizando JPQL (Java Persistence Query Language)
    @Query("SELECT p FROM HistoricoTransacciones p WHERE p.usuario.nombre = ?1")
    List<HistoricoTransacciones>findHistoricoTransaccionesPorNombreUsuario(String nombreUsuario);
}
