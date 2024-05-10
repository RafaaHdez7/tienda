package backend.rafhergom.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.Negocio;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    // Métodos CRUD generados automáticamente
	List<Negocio> findByUsuarioId(Long usuarioId);

}
