package com.chocolatada.auth.service.mail;

public interface IMailService {

    void sendVerificationEmail(String email, String verificationLink);

    void sendLoginNotification(String email);

    void sendEmail(String toEmail, String subject, String body);
}
