package charlie.logic.impl;

import charlie.logic.UserLogic;
import charlie.dao.UserAccess;
import charlie.dto.UserDto;
import charlie.entity.UserEntity;
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

    @Resource
    private EJBContext ejbContext;

    private UserEntity caller;

    @AroundInvoke
    private Object getCaller(InvocationContext ctx) throws Exception {
        Principal p = ejbContext.getCallerPrincipal();
        if (p != null) {
            caller = ua.getUser(p.getName());
        }
        return ctx.proceed();
    }

    @Override
    @RolesAllowed(USER_ROLE)
    public UserDto getCurrentUser() {
        return createDTO(caller);
    }

    private UserDto createDTO(UserEntity ue) {
        return new UserDto(ue.getUuid(), ue.getJpaVersion(), ue.getUsername(), ue.getFirstName(), ue.getLastName());
    }

}
