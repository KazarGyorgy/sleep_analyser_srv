package sleepanalyser.srv.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sleepanalyser.srv.Dto.*;
import sleepanalyser.srv.Entities.Rating;
import sleepanalyser.srv.Entities.SleepingData;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.services.MockSleepDataService;
import sleepanalyser.srv.services.RatingService;
import sleepanalyser.srv.services.SleepDataService;
import sleepanalyser.srv.services.UserService;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sleep-data")
@Slf4j
@RequiredArgsConstructor
public class SleepDataController {
    @Autowired
    private MockSleepDataService mockSleepDataService;
    @Autowired
    private SleepDataService sleepDataService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RatingService ratingService;

@PostMapping()
public  ResponseEntity<RatingDTO> saveRating(@RequestBody RatingDTO dto){
    Rating save = this.ratingService.save(dto);
    return ResponseEntity.ok().body(convertToRatingDto(save));
}
    @GetMapping("/get/{userId}/{day}")
    public ResponseEntity<ExtendedSleepingDataDto>
    getUserSleepingData(@PathVariable String userId, @PathVariable String day)
            throws ParseException, IllegalArgumentException {

        Date startDate;

        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd");
            startDate = format.parse(day);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }

        List<SleepingData> sleepingDataList =
                this.sleepDataService.getSleepingDataByUserAndDay(Long.parseLong(userId), startDate);
        /**
         * If no data available this selected day, calling the mock data generator.
         */
        if (sleepingDataList.isEmpty()) {
            log.info("No data available, calling Mock generator.");
            mockSleepDataService.generateData(Long.parseLong(userId), startDate);
            sleepingDataList =
                    this.sleepDataService.getSleepingDataByUserAndDay(Long.parseLong(userId), startDate);
        }
        List<SleepingDataDTO> dto = sleepingDataList.stream().map(this::convertToDTO).collect(Collectors.toList());
        dto = this.mockSleepDataService.calculateMovingAverage(dto);

        int maxOxy = dto.stream().max(Comparator.comparing(SleepingDataDTO::getBloodOxygen)).get().getBloodOxygen();
        int minOxy = dto.stream().min(Comparator.comparing(SleepingDataDTO::getBloodOxygen)).get().getBloodOxygen();
        Double avgOxy = dto.stream()
                .mapToDouble(a -> a.getBloodOxygen())
                .average().getAsDouble();

        ExtendedSleepingDataDto extendedSleepingDataDto = new ExtendedSleepingDataDto(dto, maxOxy, minOxy, minOxy,
                avgOxy.toString());
        this.sleepDataService.analyseSleepingData(Long.parseLong(userId), extendedSleepingDataDto);
        return ResponseEntity.ok().body(extendedSleepingDataDto);
    }

    @GetMapping("/get/weekly/{userId}/{day}")
    public ResponseEntity<List<SleepLengthDto>> weeklyStat(@PathVariable String userId, @PathVariable String day) {
        Date startDate;
        Date from;
        Date to;
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd");
            startDate = format.parse(day);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        from = cal.getTime();
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        to = cal.getTime();
        List<SleepLengthDto> dto = sleepDataService.getSleepingDataByRange(Long.valueOf(userId), from, to);
        return ResponseEntity.ok().body(dto);

    }

    @GetMapping("/get/monthly/{userId}/{day}")
    public ResponseEntity<List<SleepLengthDto>> monthlyStat(@PathVariable String userId, @PathVariable String day) {
        Date startDate;
        Date from;
        Date to;
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd");
            startDate = format.parse(day);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_MONTH, 1);

        from = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        to = cal.getTime();

        List<SleepLengthDto> dto = sleepDataService.getSleepingDataByRange(Long.valueOf(userId), from, to);
        return ResponseEntity.ok().body(dto);

    }

    /**
     * Convert SleepindData entity to DTO.
     *
     * @param data SleepingData.
     * @return SleepDataDTO.
     */
    private SleepingDataDTO convertToDTO(SleepingData data) {
        SleepingDataDTO dto = modelMapper.map(data, SleepingDataDTO.class);
        dto.setBloodOxygen(data.getBloodOxygen());
        dto.setHR(data.getHeartRate());
        dto.setDateTimeOfMeasurement(Date.from(data.getDate().atZone(ZoneId.systemDefault()).toInstant()));
        return dto;
    }

    public RatingDTO convertToRatingDto(Rating rating) {
        RatingDTO dto = modelMapper.map(rating, RatingDTO.class);
        dto.setRating(rating.getRating());
        dto.setDate(rating.getDate());
        dto.setRatingMessage(rating.getRatingMessage());
        return dto;
    }
}
