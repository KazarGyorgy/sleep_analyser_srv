package sleepanalyser.srv.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class SleepLengthDto
{
    private LocalDate dateOfMeasurement;
    private float lengthOfSleep;
}
