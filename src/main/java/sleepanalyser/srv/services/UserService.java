package sleepanalyser.srv.services;

import org.springframework.data.crossstore.ChangeSetPersister;
import sleepanalyser.srv.Entities.User;
import java.util.List;
import java.util.Optional;

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

    /**
     * Get users by role name
     * @param roleName name of the role.
     * @return List of users
     */
    List<User> getUsersByRoleName(String roleName);

    /**
     * Delete user
     * @param userId Long user id.
     * @return boolean
     */
    boolean deleteUser(Long userId);

    /**
     * Update user entity.
     * @param userId userId;
     * @param user The modified data-
     * @return a saved user entity.
     * @throws ChangeSetPersister.NotFoundException
     */
    User updateUser(String userId, User user) throws ChangeSetPersister.NotFoundException;

    /**
     * Change Password.
     * @param oldPassword .
     * @param newPassword .
     * @return boolean after save.
     */
    boolean changePassword( String oldPassword, String newPassword);

    /**
     * Find user by id;
     * @param userId userid.
     * @return Opitonal user.
     */
    Optional<User> findById(Long userId);
}
