package backend.rafhergom.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos CRUD generados automáticamente

    // Ejemplo de método de consulta personalizado
    List<Usuario> findByNombre(String nombre);

    // Otra consulta personalizada utilizando JPQL (Java Persistence Query Language)
    @Query("SELECT u FROM Usuario u WHERE u.rol = ?1")
    List<Usuario> findUsuariosPorRol(String rol);
    
    @Query("SELECT u.rol FROM Usuario u WHERE u.nombre = :username AND u.contrasena = :password")
    String findRoleByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    @Query("SELECT u.contrasena FROM Usuario u WHERE u.nombre = :username ")
    String findPasswordByUsername(@Param("username") String username);
    
}

