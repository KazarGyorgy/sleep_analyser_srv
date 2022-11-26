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
    private String lengthOfSleep;
}
