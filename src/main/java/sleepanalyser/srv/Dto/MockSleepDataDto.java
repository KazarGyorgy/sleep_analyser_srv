package sleepanalyser.srv.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class MockSleepDataDto {
    int hours;
    int minutes;
}
