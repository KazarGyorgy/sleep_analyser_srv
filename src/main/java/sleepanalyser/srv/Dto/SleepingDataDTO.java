package sleepanalyser.srv.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class SleepingDataDTO {
    Integer BloodOxygen;
    Integer HR;
    Date dateTimeOfMeasurement;
}
