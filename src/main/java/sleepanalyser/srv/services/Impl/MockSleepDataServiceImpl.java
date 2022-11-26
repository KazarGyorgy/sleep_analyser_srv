package sleepanalyser.srv.services.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Dto.MockSleepDataDto;
import sleepanalyser.srv.Dto.SleepingDataDTO;
import sleepanalyser.srv.Entities.SleepingData;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.SleepingDataRepository;
import sleepanalyser.srv.services.MockSleepDataService;
import sleepanalyser.srv.services.UserService;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class MockSleepDataServiceImpl implements MockSleepDataService {
    @Autowired
    private final UserService userService;
    private final SleepingDataRepository sleepingDataRepository;

    @Override
    /**
     * This function generates sleep data for the given day;
     * @param sleepStartDate The Day when sleeping started
     */
    public void generateData(Long userId, Date sleepStartDate) throws ParseException {

        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        MockSleepDataDto sleepStart = this.generateSleepStart();
        MockSleepDataDto sleepLength = this.generateSleepLength();

        Date startDate = this.setHoursAndMinutesToDate(sleepStartDate, sleepStart.getHours(), sleepStart.getMinutes());
        Date endDate = this.addHoursToDate(startDate, sleepLength.getHours(), sleepLength.getMinutes());

        List<SleepingData> dataList = new ArrayList<>();

        for (LocalDateTime date = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime(); date.isBefore(endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()); date = date.plusMinutes(5)) {
            int hr = dataList.size() % 7 == 0
                    ? this.generateRandom(55, 80)
                    : this.generateRandomRefine(dataList.get(dataList.size() - 1).getHeartRate());

            int bloodOxygen = this.generateBoodOxygen();
            SleepingData data = new SleepingData();
            data.setUser(user);
            data.setDate(date);
            data.setBloodOxygen(bloodOxygen);
            data.setHeartRate(hr);

            dataList.add(data);
        }
        log.info(dataList.toString());
        sleepingDataRepository.saveAll(dataList);
    }


    /**
     * This function generates a time, when the sleep started;
     *
     * @return SleepDataDto hour and minutes when the sleep begins.
     */
    private MockSleepDataDto generateSleepStart() {
        int minStart = 19;
        int maxStart = 24;

        int startRange = maxStart - minStart;
        float sleepBegins = (float) ((float) Math.round(((Math.random() * startRange) + minStart) * 100d) / 100d);
        int hours = (int) sleepBegins;
        int minutes = (int) (60 * (sleepBegins - hours));

        log.info("The beginning of sleep : {} h : {}m  ({})", hours, minutes, sleepBegins);

        return new MockSleepDataDto(hours, minutes);

    }

    /**
     * This function generates sleep length.
     *
     * @return SleepDataDto which contains the sleep length(hours and minutes)
     */
    private MockSleepDataDto generateSleepLength() {
        int minSleeping = 6;
        int maxSleeping = 10;
        int sleepingRange = maxSleeping - minSleeping + 1;

        float sleepingTime = (float) ((float) Math.round(((Math.random() * sleepingRange) + minSleeping) * 100d) / 100d);
        int sleepingHours = (int) sleepingTime;
        int sleepingMinutes = (int) (60 * (sleepingTime - sleepingHours));

        log.info("Length of sleep : {}:{} ({})", sleepingHours, sleepingMinutes, sleepingTime);

        return new MockSleepDataDto(sleepingHours, sleepingMinutes);
    }

    /**
     * this function add huor and minutes to a date Object.
     *
     * @param date    The day of sleep begins.
     * @param hours   The hour of sleep begins.
     * @param minutes The minute of sleep begins.
     * @return Date Object
     */
    private Date addHoursToDate(Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    /**
     * This function add Hours and minutes to a given Date.
     *
     * @param date    The date of sleep begins
     * @param hours   the sleep length in hours.
     * @param minutes the sleep length in minutes;
     * @return a date Object which represented the sleep end.
     */
    private Date setHoursAndMinutesToDate(Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    /**
     * This function generates a random number based on the incoming parameters
     *
     * @param from from value
     * @param to   to value
     * @return Int of random number
     */
    private int generateRandom(int from, int to) {
        int range = to - from;
        int rand = (int) (Math.round(((Math.random() * range) + from)));

        return rand;
    }

    private int generateBoodOxygen() {
        double random = Math.random();
        int bOxygen;
        if (random > 0.00001) {
            bOxygen = (int) (Math.random() * 4) + 96;
        } else {
            bOxygen = (int) (Math.random() * 8) + 90;
        }
        return bOxygen;
    }

    private int generateRandomRefine(int from) {
        int range;
        if (Math.round(((Math.random()))) > 0.5) {
            range = 5;
        } else {
            range = -5;
        }
        int rand = (int) (Math.round(((Math.random() * range) + from)));
        if (rand < 37 || rand > 80) {
            rand = 60;
        }
        return rand;
    }

    public List<SleepingDataDTO> calculateMovingAverage(List<SleepingDataDTO> dataList) {
        int sum = 0;
        int iterator;
        for (iterator = 1; iterator < dataList.size(); iterator++) {
            if (iterator < 3) {
                dataList.get(iterator).setMa3(dataList.get(iterator).getHR());
            } else {
                int ma3 = 0;
                ma3 = (dataList.get(iterator).getHR() + dataList.get(iterator - 1).getHR()
                        + dataList.get(iterator - 2).getHR()) / 3;
                dataList.get(iterator).setMa3(ma3);
            }
            if (iterator < 5) {
                dataList.get(iterator).setMa5(dataList.get(iterator).getHR());
            } else {
                int ma5 = 0;
                ma5 = (dataList.get(iterator).getHR() + dataList.get(iterator - 1).getHR()
                        + dataList.get(iterator - 2).getHR()
                        + dataList.get(iterator - 3).getHR()
                        + dataList.get(iterator - 4).getHR()) / 5;
                dataList.get(iterator).setMa5(ma5);
            }
        }

        return dataList;
    }
}




