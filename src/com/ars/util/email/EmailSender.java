package com.ars.util.email;

import com.ars.system.APPStatics;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by JJ on 9/2/16.
 */
public class EmailSender {

    public static void sendEmail(String to, String subject, String content){

        final String username = "airlinereservationcompany@gmail.com";
        final String password = "1qaz2wsx#";
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttsl.enable","true");
        properties.setProperty("mail.smtp.port","25");

        Session session = Session.getInstance(properties,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("airlinereservationcompany@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        }catch (MessagingException e){
            e.getMessage();
            System.out.println(e.getMessage());
        }
    }

}
