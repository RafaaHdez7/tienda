package rafhergom.backend.tfg.shared;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDtoConverter<E, D> {

    public abstract E fromDto(D dto);

    public abstract D fromEntity(E entity);

    public List<D> convertToDtos(List<E> entities) {
        return entities.stream()
            .map(this::fromEntity)
            .collect(Collectors.toList());
    }

    public List<E> convertToEntities(List<D> dtos) {
        return dtos.stream()
            .map(this::fromDto)
            .collect(Collectors.toList());
    }
    
}
