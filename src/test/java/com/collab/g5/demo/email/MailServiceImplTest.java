package com.collab.g5.demo.email;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MailServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MailServiceImplTest {
    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Test
    void testSendEmail() throws UnsupportedEncodingException, MessagingException, MailException {
        doNothing().when(this.javaMailSender).send((MimeMessage) any());
        when(this.javaMailSender.createMimeMessage())
                .thenReturn(new MimeMessage(new MimeMessage(new MimeMessage(new MimeMessage(new MimeMessage((Session) null,
                        new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))))))));

        Mail mail = new Mail();
        mail.setMailContent("Not all who wander are lost");
        mail.setContentType("text/plain");
        mail.setAttachments(new ArrayList<Object>());
        mail.setMailTo("alice.liddell@example.org");
        mail.setMailBcc("ada.lovelace@example.org");
        mail.setMailFrom("jane.doe@example.org");
        mail.setMailCc("ada.lovelace@example.org");
        mail.setMailSubject("Hello from the Dreaming Spires");
        this.mailServiceImpl.sendEmail(mail);
        verify(this.javaMailSender).createMimeMessage();
        verify(this.javaMailSender).send((MimeMessage) any());
    }

    @Test
    void testSendEmail2() throws UnsupportedEncodingException, MessagingException, MailException {
        doNothing().when(this.javaMailSender).send((MimeMessage) any());
        when(this.javaMailSender.createMimeMessage())
                .thenReturn(new MimeMessage(new MimeMessage(new MimeMessage(new MimeMessage(new MimeMessage((Session) null,
                        new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))))))));

        Mail mail = new Mail();
        mail.setMailContent("Not all who wander are lost");
        mail.setContentType("text/plain");
        mail.setAttachments(new ArrayList<Object>());
        mail.setMailTo("Mail To");
        mail.setMailBcc("ada.lovelace@example.org");
        mail.setMailFrom("jane.doe@example.org");
        mail.setMailCc("ada.lovelace@example.org");
        mail.setMailSubject("Hello from the Dreaming Spires");
        this.mailServiceImpl.sendEmail(mail);
        verify(this.javaMailSender).createMimeMessage();
    }
}

