package sleepanalyser.srv.services;

import sleepanalyser.srv.Dto.ExtendedSleepingDataDto;
import sleepanalyser.srv.Dto.SleepLengthDto;
import sleepanalyser.srv.Dto.SleepingDataDTO;
import sleepanalyser.srv.Entities.SleepingData;
import sleepanalyser.srv.Entities.User;

import java.util.Date;
import java.util.List;

/**
 * This service get the Sleeping Data.
 */
public interface SleepDataService {

    List<SleepingData> getSleepingDataByUserAndDay(Long userId, Date day);

    void analyseSleepingData(Long userId, ExtendedSleepingDataDto dto);
    List<SleepLengthDto> getSleepingDataByRange(Long userId, Date from, Date to);
}
