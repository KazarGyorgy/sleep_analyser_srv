package sleepanalyser.srv.services;

import sleepanalyser.srv.Dto.RatingDTO;
import sleepanalyser.srv.Entities.Rating;
import sleepanalyser.srv.Entities.User;

import java.util.Date;
import java.util.Optional;

public interface RatingService {
    Rating save(RatingDTO rating);
    Optional<Rating> getByDateAndUser(Date date,String userId);
}
