package sleepanalyser.srv.services;

import sleepanalyser.srv.Dto.RatingDTO;
import sleepanalyser.srv.Entities.Rating;

public interface RatingService {
    Rating save(RatingDTO rating);
}
