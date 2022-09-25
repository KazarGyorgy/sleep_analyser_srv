package sleepanalyser.srv.Services;

import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);

    User getUserByTaj(Long taj);

}
