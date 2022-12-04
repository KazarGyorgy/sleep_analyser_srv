package sleepanalyser.srv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sleepanalyser.srv.Entities.Rating;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByDateBetweenAndUserId(Date startDate, Date endDate, Long userId);
}
