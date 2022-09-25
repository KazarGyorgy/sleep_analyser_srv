package sleepanalyser.srv.Services;

import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);

    void addRole2User(Long Taj, String rolename);

    User getUserByTaj(Long taj);

    List<User> getUsers();

}
