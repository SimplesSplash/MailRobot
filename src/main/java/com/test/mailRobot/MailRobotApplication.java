package com.test.mailRobot;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailRobotApplication implements CommandLineRunner{
    
    @Autowired
    MailRobot mailRobot;

    public static void main(String[] args) {
        SpringApplication.run(MailRobotApplication.class, args);
        
       
    }

    @Override
    public void run(String... args) throws Exception {
         try {
           
            mailRobot.setAddress("alex.sokolovsky00@mail.ru");
            mailRobot.setPassword("1945100597f");
            mailRobot.setImapHost("mail.ru");
            mailRobot.setSubject("Тест");

            Set<String> schema = new HashSet<>();
            schema.add("Имя");
            schema.add("Фамилия");
            schema.add("Текст");
            mailRobot.setSchema(schema);
            
            List<HashMap<String, Object>> mwssages = mailRobot.getMessagesContent();

            System.out.println(mwssages);
        } catch (MessagingException | IOException ex) {
            
        }
    }

}
