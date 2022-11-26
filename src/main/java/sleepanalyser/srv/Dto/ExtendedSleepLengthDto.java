package sleepanalyser.srv.Dto;

import lombok.Data;

import java.util.List;

@Data
public class ExtendedSleepLengthDto extends SleepLengthDto {
    List<SleepLengthDto> listOfDailySleepingLength;
    float minimumDailySleep;
    float maximumDailySleep;
    float averageSleepInRange;
}
