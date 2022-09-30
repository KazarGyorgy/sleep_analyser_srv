package sleepanalyser.srv.services;

import sleepanalyser.srv.Entities.User;

import java.util.List;

public interface UserService {

    /**
     * This function save a new user.
      * @param user
     * @return the saved user entity.
     */
    User saveUser(User user);

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

    List<User> getUsers();

}
