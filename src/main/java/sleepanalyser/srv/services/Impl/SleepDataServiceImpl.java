package sleepanalyser.srv.services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Entities.SleepingData;

import sleepanalyser.srv.Repositories.SleepingDataRepository;
import sleepanalyser.srv.services.SleepDataService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SleepDataServiceImpl implements SleepDataService {
    @Autowired
    SleepingDataRepository sleepingDataRepository;

    @Override
    public List<SleepingData> getSleepingDataByUserAndDay(Long userId, Date day) {
    Date startDate = addHoursToDate(day,12,00);
    Date endDate = addHoursToDate(startDate,23,00);
    log.info("Start: {} End: {}",startDate,endDate);
    return this.sleepingDataRepository
            .findAllByUserIdAndDateBetween(userId,
                    LocalDateTime.ofInstant(startDate.toInstant(),ZoneId.systemDefault()),
                    LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()));
    }

    private Date addHoursToDate(Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        log.info(calendar.getTime().toString());

        return  calendar.getTime();
    }
}
