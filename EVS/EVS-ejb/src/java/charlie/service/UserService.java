package charlie.service;

import charlie.dao.UserAccess;
import charlie.entity.UserEntity;
import charlie.logic.UserLogic;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class UserService {

    private Map<String, UserEntity> userMap = new HashMap<>();

    @EJB
    private UserAccess userDao;
    @EJB
    private UserLogic ul;

    public UserEntity findByUsername(String username) {
        if (!userMap.containsKey(username)) {
            UserEntity userEntity = userDao.getUser(username);
            if (userEntity == null) {
                return null;
            }

            userMap.putIfAbsent(username, userEntity);
        }

        return userMap.get(username);
    }
    
    
    public UserEntity getCurrentLoggedInUser() {
        return findByUsername(ul.getCurrentUsername());
    }
}
