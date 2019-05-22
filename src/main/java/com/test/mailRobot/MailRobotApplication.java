package com.test.mailRobot;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailRobotApplication.class, args);

        try {
            MailRobot mailRobot = new MailRobot();
            mailRobot.setAddress("alex.sokolovsky00@mail.ru");
            mailRobot.setPassword("1945100597f");
            mailRobot.setImapHost("imap.mail.ru");
            mailRobot.setSubject("Test");

            Set<String> schema = new HashSet<>();
            schema.add("Имя");
            schema.add("Фамилия");
            schema.add("Текст");
            mailRobot.setSchema(schema);
            
            List<HashMap<String, Object>> mwssages = mailRobot.getMessages();

            System.out.println(mwssages);
        } catch (MessagingException | IOException ex) {
            
        }
    }

}
