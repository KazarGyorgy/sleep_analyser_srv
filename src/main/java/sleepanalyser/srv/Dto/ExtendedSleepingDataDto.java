package sleepanalyser.srv.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExtendedSleepingDataDto{
    private  List<SleepingDataDTO> sleepingData;
    private int maxOxygen;
    private int minOxygen;
    private int averageOxygen;
    private float rem;
    private float deep;
    private float light;
    private float lengthOfSleep;
    private int rating;
    private String ratingMessage;
}
