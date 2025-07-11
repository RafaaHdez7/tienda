package backend.rafhergom.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.DetallesPedido;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Long> {
    // Métodos CRUD generados automáticamente
	List<DetallesPedido> getByPedidoId(long pedidoId);
}
