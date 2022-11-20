package sleepanalyser.srv.services;

import sleepanalyser.srv.Dto.SleepingDataDTO;
import sleepanalyser.srv.Entities.SleepingData;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface MockSleepDataService {
    /**
     * This function generates sleep data for the given day;
     * @param sleepStartDate The Day when sleeping started
     */
    void generateData(Long userId, Date sleepStartDate) throws ParseException;

    List<SleepingDataDTO> calculateMovingAverage(List<SleepingDataDTO> dataList);
}
