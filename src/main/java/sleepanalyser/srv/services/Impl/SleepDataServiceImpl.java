package sleepanalyser.srv.services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Dto.ExtendedSleepLengthDto;
import sleepanalyser.srv.Dto.ExtendedSleepingDataDto;
import sleepanalyser.srv.Dto.SleepLengthDto;
import sleepanalyser.srv.Entities.SleepingData;

import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.SleepingDataRepository;
import sleepanalyser.srv.Repositories.UserRepository;
import sleepanalyser.srv.services.SleepDataService;

import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class SleepDataServiceImpl implements SleepDataService {
    @Autowired
    SleepingDataRepository sleepingDataRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<SleepingData> getSleepingDataByUserAndDay(Long userId, Date day) {
        Date startDate = addHoursToDate(day, 12, 00);
        Date endDate = addHoursToDate(startDate, 23, 00);
        log.info("Start: {} End: {}", startDate, endDate);
        return this.sleepingDataRepository
                .findAllByUserIdAndDateBetween(userId,
                        LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()),
                        LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public void analyseSleepingData(Long userId, ExtendedSleepingDataDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        int normalHRLevel;
        int userAge = this.calculateAge(user.getBirthdate(), new Date());
        if (userAge < 50) {
            normalHRLevel = 64;
        } else if (userAge > 50 && userAge < 60) {
            normalHRLevel = 68;
        } else {
            normalHRLevel = 73;
        }
        this.calculateSleepTypesLength(dto);
        log.info(normalHRLevel + "normal hr level" + "age: " + userAge);
    }

    @Override
    public List<SleepLengthDto> getSleepingDataByRange(Long userId, Date from, Date to) {
       List<SleepLengthDto> sleepLengthDtos =  new ArrayList<SleepLengthDto>() ;

        log.info(from + "fromdate" + to + "end date");
        for (LocalDate date = from.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(); date.isBefore(to.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()); date = date.plusDays(1)) {
            LocalDateTime startDateTime = date.atTime(6, 0);
            LocalDateTime endDateTime = startDateTime.plusDays(1);
            List<SleepingData> sleepingData = this.sleepingDataRepository
                    .findAllByUserIdAndDateBetween(userId, startDateTime, endDateTime);
            SleepLengthDto dto = new SleepLengthDto();
            dto.setDateOfMeasurement(date);

            float lengthOfSleep = 0.00F;

            lengthOfSleep =  (float) (sleepingData.size() * 5) / 60;

            dto.setLengthOfSleep(lengthOfSleep);
            sleepLengthDtos.add(dto);
        }


        return sleepLengthDtos;
    }

    private Date addHoursToDate(Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        log.info(calendar.getTime().toString());

        return calendar.getTime();
    }


    public int calculateAge(Date birthDate, Date currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(), currentDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()).getYears();
        } else {
            return 0;
        }
    }

    public void calculateSleepTypesLength(ExtendedSleepingDataDto dto) {
        AtomicInteger deep = new AtomicInteger();
        int light = 0;
        AtomicInteger rem = new AtomicInteger();
        dto.getSleepingData().forEach((data) -> {
            if (data.getMa3() != null && data.getMa5() != null) {
                if (data.getMa3() > data.getMa5()) {
                    rem.addAndGet(5);
                } else {
                    deep.addAndGet(5);
                }
            }
        });

        log.info("deep" + deep + "rem" + rem);
    }

}
