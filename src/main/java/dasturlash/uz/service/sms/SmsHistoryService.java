package dasturlash.uz.service.sms;

import dasturlash.uz.entities.sms.SmsHistoryEntity;
import dasturlash.uz.exceptions.BadRequestException;
import dasturlash.uz.repository.sms.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void save(String phone, String body, String code) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhoneNumber(phone);
        entity.setBody(body);
        entity.setCode(code);
        entity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(entity);
    }

    public SmsHistoryEntity getSmsByPhone(String phoneNumber) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneNumberOrderByCreatedDateDesc(phoneNumber);
        if (optional.isEmpty()) {
            throw new BadRequestException("Invalid phone number");
        }
        return optional.get();
    }

    public void increaseAttempt(String id) {
        smsHistoryRepository.increaseAttempt(id);
    }

    public boolean isSmsSendToPhone(String phone, String code) {
        SmsHistoryEntity smsHistoryEntity = getSmsByPhone(phone);
        // expired time
        LocalDateTime expiredTime = smsHistoryEntity.getCreatedDate().plusMinutes(1);
        // check time
        if (LocalDateTime.now().isAfter(expiredTime)) {
            return false;
        }
        // get attempts
        Integer attempts = smsHistoryEntity.getAttemptCount();
        // check attempt count
        if (attempts >= 5) {
            return false;
        }
        if (!code.equals(smsHistoryEntity.getCode())) {
            //increase attempt count
            increaseAttempt(smsHistoryEntity.getId());
            return false;
        }
        return true;
    }
}
