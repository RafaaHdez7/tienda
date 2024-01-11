package backend.rafhergom.tfg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.Negocio;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    // Métodos CRUD generados automáticamente

}
