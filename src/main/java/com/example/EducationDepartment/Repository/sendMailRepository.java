package com.example.EducationDepartment.Repository;

import javax.mail.MessagingException;

import com.example.EducationDepartment.Model.Mail;

/**
 * 
 * @author Rais Ahmad
 * @date 29/10/2021
 * @Discription Mail Repository
 * 
 */
public interface sendMailRepository {
	void sendMail(Mail mail);

    void sendMailWithAttachments(Mail mail) throws MessagingException;
}
