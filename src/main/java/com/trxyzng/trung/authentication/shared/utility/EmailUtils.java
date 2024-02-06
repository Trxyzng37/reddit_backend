package com.trxyzng.trung.authentication.shared.utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    private static final String fromEmail = "testting3737@gmail.com";
    private static final String password = "syon wipj mase evcy";
//    private static final String host = "smtp.gmail.com";
//    private static final int port = 587;

    private static Properties setProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); //TLS
        return properties;
    }
    private static Session createSession(Properties properties) {
        Session session = Session.getInstance(
            properties,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            }
        );
        return session;
    };
    public static void sendEmail(String email, String subject, String bodyText) {
        try {
            System.out.println("Try to send email");
            Properties properties = setProperties();
            Session session = createSession(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(email)
            );
            message.setSubject(subject);
//            message.setText(bodyText);
            message.setContent(bodyText, "text/html");
            Transport.send(message);
            System.out.println("Body of email: " + bodyText);
        }
        catch (MessagingException e) {
            System.out.println("Error send mail");
            System.out.println(e);
        }
    }
}
