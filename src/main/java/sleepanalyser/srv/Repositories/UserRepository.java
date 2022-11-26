package sleepanalyser.srv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sleepanalyser.srv.Entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAll();
    List<User> findByRolesName(String rolename);
}
