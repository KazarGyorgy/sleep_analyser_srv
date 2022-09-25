package sleepanalyser.srv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sleepanalyser.srv.Entities.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByTajNumber(Long Taj);
}
