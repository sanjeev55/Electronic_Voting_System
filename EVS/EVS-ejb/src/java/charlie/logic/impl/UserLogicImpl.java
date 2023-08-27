package charlie.logic.impl;

import charlie.logic.UserLogic;
import charlie.dao.UserAccess;
import charlie.dto.UserDto;
import charlie.entity.RoleEnum;
import charlie.entity.UserEntity;
import charlie.mapper.UserEntityMapper;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class UserLogicImpl implements UserLogic {

    @EJB
    private UserAccess ua;
    
    @EJB
    private UserEntityMapper userEntityMapper;

    @Resource
    private EJBContext ejbContext;

    private UserEntity caller;

    @AroundInvoke
    private Object getCaller(InvocationContext ctx) throws Exception {
        String username = getCurrentUsername();
        if (username != null) {
            caller = ua.getUser(username);
        }
        return ctx.proceed();
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public UserDto getCurrentUser() {
        return userEntityMapper.toDto(caller);
    }

    @Override
    public String getCurrentUsername() {
        Principal p = ejbContext.getCallerPrincipal();
        if(p == null)
            return null;
        
        return p.getName();
    }
    
    @Override
    @RolesAllowed(USER_ROLE)
    public void updateUserRole(String uuid, String role) {
        UserEntity ue = ua.getByUuid(uuid);
        
        if(role.equals("STAFF")){
        ue.setRole(RoleEnum.ROLE_ORGANIZER);
        }else if(role.equals("ADMIN")){
        ue.setRole(RoleEnum.ROLE_ADMINISTRATOR);
        }
        
        ua.edit(ue);
    }
    
    @Override
    public UserDto getUserByUsername(String username){
        UserEntity ue = ua.findUserByUsername(username);
        
        return userEntityMapper.toDto(ue);
    }
    
    @Override
    public List<UserDto> getAllUsers(){
        List<UserEntity> entities = ua.findAllUsers();
        return entities.stream().map(userEntityMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void addUser(UserDto userDto) {
        UserEntity ue = userEntityMapper.toEntity(userDto);
        ua.save(ue);
    }
    
    @Override
    public UserDto getUserById(int userId){
        UserEntity ue = ua.find(userId);
        return userEntityMapper.toDto(ue);
    }
    
    @Override
    public List<UserDto> getUserHavingRoleOrganizers() {
      var entities = ua.findAllUsersHavingRoleOrganizers();
        System.out.println("entitiesL " + entities);
      if(entities == null) 
          return Collections.emptyList();
      
      return userEntityMapper.toDtoList(entities);
    }
    
    @Override
    public void deleteById(UserDto userDto){
        ua.remove(userEntityMapper.toEntity(userDto));
    }

}
