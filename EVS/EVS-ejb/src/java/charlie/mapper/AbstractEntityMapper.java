package charlie.mapper;

import charlie.dto.AbstractDto;
import charlie.entity.AbstractEntity;


public abstract class AbstractEntityMapper<E extends AbstractEntity, D extends AbstractDto> {

    private E entity;
    private D domain;
    
    public AbstractEntityMapper(E entity, D domain) {
        this.entity = entity;
        this.domain = domain;
    }
    
    public E toEntity(D domain) {
        entity.setId(domain.getId());
        entity.setJpaVersion(domain.getJpaVersion());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setUuid(domain.getUuid());
        return entity;
    }

    public D toDto(E entity) {
        domain.setId(entity.getId());
        domain.setJpaVersion(entity.getJpaVersion());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setUuid(entity.getUuid());
        return domain;
    }

}

