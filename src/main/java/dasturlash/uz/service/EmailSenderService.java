package dasturlash.uz.service;

import dasturlash.uz.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryService emailHistoryService;

    public void sendRegistrationEmail(String toAccount) {
        Integer smsCode = RandomUtil.fiveDigit();
        String body = "Your code is: " + smsCode;
        // TODO make toAccount adn smsCode encoded using jwt and send it
        // send
        sendSimpleMessage("Registration complete", body, toAccount);
        // save to db
        emailHistoryService.create(body, smsCode, toAccount);
    }

    public void sendRegistrationStyledEmail(String toAccount) {
        Integer smsCode = RandomUtil.fiveDigit();
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"text-align: center\">Kun.uz portaliga xush kelibsiz.</h1>\n" +
                "<br>\n" +
                "<h4>Ro'yhatdan o'tishni tugatish uchun  tastiqlash code: </h4>\n" +
                " <p> <b>%d</b> </p>" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        // TODO make toAccount adn smsCode encoded using jwt and send it
        body = String.format(body, smsCode);
        // send
        sendMimeMessage("Registration complete", body, toAccount);
        // save to db
        emailHistoryService.create(body, smsCode, toAccount);
    }

    private String sendSimpleMessage(String subject, String body, String toAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

        return "Mail was send";
    }

    private String sendMimeMessage(String subject, String body, String toAccount) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Mail was send";
    }
}
