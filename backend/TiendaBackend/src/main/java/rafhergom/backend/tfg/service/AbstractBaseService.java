package rafhergom.backend.tfg.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;

import rafhergom.backend.tfg.shared.AbstractDtoConverter;

import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseService<E, D, DC extends AbstractDtoConverter<E, D>, R extends JpaRepository<E, ID>, ID> {

    @Autowired
    protected R repository;

    @Autowired
    protected DC dtoConverter;

    public D findById(ID id) {
		/* Utilizar Optional para manejar posibles valores nulos al buscar por ID. Esto mejora la seguridad del código al evitar NullPointerException si no se encuentra una entidad con el ID proporcionado.*/
        Optional<E> entity = repository.findById(id);
        return entity.map(dtoConverter::fromEntity).orElse(null);
		/* Utilizar referencias de métodos, como dtoConverter::fromEntity, para simplificar la llamada a métodos y hacer que el código sea más claro.*/
    }

    public List<D> findAll() {
        List<E> entities = repository.findAll();
        List<D> dtos = new ArrayList<>();

        for (E entity : entities) {
            dtos.add(dtoConverter.fromEntity(entity));
        }

        return dtos;
    }

    public D save(D d) {
        E entity = dtoConverter.fromDto(d);
        E savedEntity = repository.save(entity);
        return dtoConverter.fromEntity(savedEntity);
    }

    public D edit(D d) {
        E entity = dtoConverter.fromDto(d);
        E savedEntity = repository.save(entity);
        return dtoConverter.fromEntity(savedEntity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

}
