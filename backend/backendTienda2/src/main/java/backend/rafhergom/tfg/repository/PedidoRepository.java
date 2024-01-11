package backend.rafhergom.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Métodos CRUD generados automáticamente

    // Ejemplo de método de consulta personalizado
    List<Pedido> findByEstadoPedido(String estado);

    // Otra consulta personalizada utilizando JPQL (Java Persistence Query Language)
    @Query("SELECT p FROM Pedido p WHERE p.usuario.id = ?1")
    List<Pedido> findPedidosPorUsuario(Long usuarioId);
}
