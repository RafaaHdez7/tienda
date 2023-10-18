package backend.rafhergom.tfg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import backend.rafhergom.tfg.model.entity.Pedido;
import backend.rafhergom.tfg.model.entity.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
