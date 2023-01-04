package com.reddit.service;

import com.reddit.entity.NotificationEmail;
import com.reddit.exception.SpringRedditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Async
    public void sendEmail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try{
    javaMailSender.send(messagePreparator);
    log.info("Activation email sent");
        }
        catch(MailException mailException){
            throw new SpringRedditException("Exception occured when sending mail to "+notificationEmail.getRecipient());
        }
    }
}
