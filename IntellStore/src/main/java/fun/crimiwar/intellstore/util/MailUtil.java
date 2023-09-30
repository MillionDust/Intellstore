package fun.crimiwar.intellstore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class MailUtil {

    @Autowired
    JavaMailSender javaMailSender;

    public void send(String from,String to,String subject,String content,boolean isHTML){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content,isHTML);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.warn("发送失败");
        }

    }

}
