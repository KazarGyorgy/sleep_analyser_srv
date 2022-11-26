package sleepanalyser.srv.Dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import sleepanalyser.srv.Entities.Rating;
import sleepanalyser.srv.Entities.User;

import java.util.Date;
@Data
public class RatingDTO {
    private Date date;
    private int rating;
    private String ratingMessage;
}
