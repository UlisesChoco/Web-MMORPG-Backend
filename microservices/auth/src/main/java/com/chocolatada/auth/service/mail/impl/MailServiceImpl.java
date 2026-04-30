package com.chocolatada.auth.service.mail.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chocolatada.auth.service.mail.IMailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements IMailService {

    private final JavaMailSender javaMailSender;

    @Value("${mail.from:noreply@chocolatada.com}")
    private String mailFrom;

    @Value("${app.name:Web MMORPG}")
    private String appName;

    @Override
    public void sendVerificationEmail(String email, String verificationLink) {
        String subject = "Verificación de correo electrónico - " + appName;
        String body = "¡Bienvenido a " + appName + "!\n\n" +
                "Para completar tu registro, haz clic en el siguiente enlace:\n" +
                verificationLink + "\n\n" +
                "Este enlace expirará en 24 horas.\n\n" +
                "Si no creaste esta cuenta, ignora este correo.\n\n" +
                "Saludos,\n" +
                "El equipo de " + appName;

        sendEmail(email, subject, body);
        log.info("Correo de verificación enviado a: "+ email);
    }

    @Override
    public void sendLoginNotification(String email) {
        String subject = "Notificación de login - " + appName;
        String body = "Hola,\n\n" +
                "Se ha detectado un login en tu cuenta de " + appName + ".\n\n" +
                "Si no fuiste tú, contacta con nuestro soporte inmediatamente.\n\n" +
                "Saludos,\n" +
                "El equipo de " + appName;

        sendEmail(email, subject, body);
        log.info("Notificación de login enviada a: "+ email);
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            log.info("Correo enviado a: "+ toEmail +" con asunto: "+ subject);

        } catch (Exception e) {
            log.error("Error al enviar correo a: "+ toEmail, e);
            throw new RuntimeException("Error al enviar correo a "+ toEmail, e);
        }
    }
}
