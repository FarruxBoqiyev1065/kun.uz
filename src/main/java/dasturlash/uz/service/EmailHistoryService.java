package dasturlash.uz.service;

import dasturlash.uz.entities.EmailHistoryEntity;
import dasturlash.uz.reposiroty.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void create(String body, int code, String toAccount) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setBody(body);
        entity.setCode(code);
        entity.setToAccount(toAccount);
        emailHistoryRepository.save(entity);
    }

    public boolean isSmsSendToAccount(String account, int code) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findLastByAccount(account);
        if (optional.isEmpty()) {
            return false;
        }
        EmailHistoryEntity entity = optional.get();
        if (!entity.getCode().equals(code)) {
            return false;
        }
        //  20:32:40           =   20:30.40  + 0:2:00
        LocalDateTime extDate = entity.getCreatedDate().plusMinutes(2);
        // now  20:31:30  >  20:32:40    |     now 20:35:30  >  20:32:40
        if (LocalDateTime.now().isAfter(extDate)) {
            return false;
        }
        return true;
    }

}
