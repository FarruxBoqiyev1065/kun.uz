package dasturlash.uz.repository.sms;

import dasturlash.uz.entities.sms.SmsHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {
    Optional<SmsHistoryEntity> findTopByPhoneNumberOrderByCreatedDateDesc(String phoneNumber);

    @Transactional
    @Modifying
    @Query("update SmsHistoryEntity set attemptCount = attemptCount + 1 where id = ?1")
    void increaseAttempt(String id);
}
