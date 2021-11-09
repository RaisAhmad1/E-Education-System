package com.example.EducationDepartment.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.EducationDepartment.Model.Mail;
import com.example.EducationDepartment.Repository.sendMailRepository;

import javax.mail.MessagingException;

@Service
public class sendMailService implements sendMailRepository {
    private final JavaMailSender javaMailSender;

    public sendMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(Mail mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getRecipient(), mail.getRecipient());

        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());

        javaMailSender.send(msg);
    }

    @Override
    public void sendMailWithAttachments(Mail mail) throws MessagingException {

    }
}
