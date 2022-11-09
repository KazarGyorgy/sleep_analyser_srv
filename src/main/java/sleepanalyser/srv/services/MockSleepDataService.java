package sleepanalyser.srv.services;

import java.text.ParseException;
import java.util.Date;

public interface MockSleepDataService {
    /**
     * This function generates sleep data for the given day;
     * @param sleepStartDate The Day when sleeping started
     */
    void generateData(Long userId, Date sleepStartDate) throws ParseException;
}
