package sleepanalyser.srv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sleepanalyser.srv.Entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
    List<User> findAll();


    List<User> findByRolesName(String rolename);
}
