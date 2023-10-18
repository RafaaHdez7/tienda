package rafhergom.backend.tfg.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rafhergom.backend.tfg.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Otros métodos de consulta personalizados, si los tienes.
}
