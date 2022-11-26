package sleepanalyser.srv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sleepanalyser.srv.Entities.SleepingData;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SleepingDataRepository extends JpaRepository<SleepingData, Long> {

    List<SleepingData> findAllByUserIdAndDateBetween(Long id, LocalDateTime endDate, LocalDateTime startDate);
}
