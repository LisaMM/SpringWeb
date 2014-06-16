/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.vdab.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author dev13
 */
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    
    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void zendMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
            logger.info("mail is verstuurd");
        } catch (MessagingException ex) {}
    }
}
