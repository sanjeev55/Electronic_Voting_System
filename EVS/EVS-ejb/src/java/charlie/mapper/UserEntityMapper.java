package charlie.mapper;

import charlie.dto.UserDto;
import charlie.entity.UserEntity;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


@Stateless
@LocalBean
public class UserEntityMapper extends AbstractEntityMapper<UserEntity, UserDto>{
    
    public UserEntityMapper() {
        super(new UserEntity(), new UserDto());
    }

    @Override
    public UserDto toDto(UserEntity entity) {
        if(entity == null)
            return null;
        
        UserDto dto = super.toDto(entity); 
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public UserEntity toEntity(UserDto domain) {
        if(domain == null)
            return null;
        
        UserEntity entity = super.toEntity(domain); 
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setRole(domain.getRole());
        entity.setUsername(domain.getUsername());
        return entity;
    }
    
    
}
