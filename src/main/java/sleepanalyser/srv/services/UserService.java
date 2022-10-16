package sleepanalyser.srv.services;

import org.springframework.data.crossstore.ChangeSetPersister;
import sleepanalyser.srv.Entities.User;
import java.util.List;

public interface UserService {

    /**
     * This function save a new user.
      * @param user
     * @return the saved user entity.
     */
    User saveUser(User user, String rolename);

    /**
     * this function mapping role and user .
     * @param username
     * @param rolename
     */
     void addUserToRole(String username, String rolename);


    /**
     * Get user entity by username
      * @param username
     * @return user
     */
    User getUserByUsername(String username);

    List<User> getUsersByRoleName(String roleName);


    void deleteUser(String userId);

    User updateUser(String userId, User user) throws ChangeSetPersister.NotFoundException;
}
