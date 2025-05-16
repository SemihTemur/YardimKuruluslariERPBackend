package com.semih.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    private String senderEmail = "semihtemur655qgmail.com";
    private String content = "Sisteme Giriş Şifreniz";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String receivedEmail, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        String messageBody =
                "Değerli Kullanıcı,\n\n" +
                        "Sisteme giriş yapabilmeniz için  şifreniz aşağıda belirtilmiştir. Lütfen bu şifreyi kimseyle paylaşmayın ve ilk girişinizde şifrenizi değiştirin.\n\n" +
                        "Şifreniz:" + password + "\n" +
                        "⚠️ Bu şifreyi güvenliğiniz için kimseyle paylaşmayın. Eğer bu şifreyi siz talep etmediyseniz, lütfen hemen destek ekibimizle iletişime geçin.\n\n" +
                        "Saygılarımızla,\n" +
                        "Destek Ekibi\n";

        message.setFrom(senderEmail);
        message.setTo(receivedEmail);
        message.setSubject(content);
        message.setText(messageBody);
        mailSender.send(message);
    }

}
