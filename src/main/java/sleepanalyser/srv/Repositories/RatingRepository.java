package sleepanalyser.srv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sleepanalyser.srv.Entities.Rating;

import java.util.Date;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByDateAndUserId(Date Date, Long userId);
}
