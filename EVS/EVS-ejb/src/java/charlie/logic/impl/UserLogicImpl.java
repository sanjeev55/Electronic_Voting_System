package charlie.logic.impl;

import charlie.logic.UserLogic;
import charlie.dao.UserAccess;
import charlie.dto.UserDto;
import charlie.entity.UserEntity;
import charlie.mapper.UserEntityMapper;
import java.security.Principal;
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

}
