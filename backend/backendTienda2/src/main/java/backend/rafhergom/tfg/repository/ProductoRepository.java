package backend.rafhergom.tfg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.rafhergom.tfg.model.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
