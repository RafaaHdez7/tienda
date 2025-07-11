package backend.rafhergom.tfg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.Monedero;

@Repository
public interface MonederoRepository extends JpaRepository<Monedero, Long> {
    // Otra consulta personalizada utilizando JPQL (Java Persistence Query Language)
    @Query("SELECT p FROM Monedero p WHERE p.usuario.id = ?1")
    Monedero findMonederoPorUsuarioId(Long usuarioId);
    
 // Otra consulta personalizada utilizando JPQL (Java Persistence Query Language)
    @Query("SELECT p FROM Monedero p WHERE p.usuario.nombre = ?1")
    Monedero findMonederoPorNombreUsuario(String nombreUsuario);
}
