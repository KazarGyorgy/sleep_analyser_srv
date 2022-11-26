package sleepanalyser.srv.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Dto.RatingDTO;
import sleepanalyser.srv.Entities.Rating;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.RatingRepository;
import sleepanalyser.srv.Repositories.UserRepository;
import sleepanalyser.srv.services.RatingService;
@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Rating save(RatingDTO dto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        Rating rating = new Rating();
        rating.setDate(dto.getDate());
        rating.setRatingMessage(dto.getRatingMessage());
        rating.setRating(dto.getRating());
        rating.setUser(user);
       return this.ratingRepository.save(rating);
    }
}
