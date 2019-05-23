/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.mailRobot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */
@Component
public class MailRobot {
//  Тема, по которой робот будет искать сообщения
    private String subject;
    
    private String address;
    
    private String password;
    
    private String imapHost;
    
//    Набор ключей, значения которых робот будет искать в тексте сообщения
    private Set<String> schema;
    
    @Autowired
    private MailParser mailParser;

    public MailRobot() {
    }

    /**
     * Устанавливает соединение с почтовым ящиком, получает сообщения из него, 
     * получает список обработанных сообщений из метода parseMessages,
     * Закрывает соединение с ящиком
     * @return Список сообщений (каждое в виде HashMap)
     * @throws MessagingException
     * @throws IOException
     */
    public List<HashMap<String, Object>> getMessagesContent() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.debug", "false");
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getInstance(properties);
        session.setDebug(false);
        Store store = session.getStore();

        store.connect(imapHost, address, password);

        Folder inbox = store.getFolder("INBOX");

        inbox.open(Folder.READ_WRITE);

        List<Message> messages = Arrays.asList(inbox.getMessages());

        List<HashMap<String, Object>> result = parseMessages(messages);
        inbox.close(true);
        store.close();

        return result;
    }

    /**
     * Метод находит сообщения с заданной темой,извлекает из них текст, 
     * передает его  в MailParser и удаляет обработанные сообщения
     * 
     * @param messages список сообщений почтового ящика
     * @return Список сообщений (каждое в виде HashMap)
     * @throws MessagingException
     * @throws IOException
     */
    public List<HashMap<String, Object>> parseMessages( List<Message> messages) throws MessagingException, IOException {
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (Message m : messages) {
            if (subject.equals(m.getSubject())) {
                Multipart mpmessage = (Multipart) m.getContent();
                BodyPart bp = mpmessage.getBodyPart(0);
                String text = bp.getContent().toString();            
                result.add(mailParser.parseMessage(text, schema));
                m.setFlag(Flags.Flag.DELETED, true);
            }
            
        }
        return result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImapHost() {
        return imapHost;
    }

    public void setImapHost(String imapHost) {
        this.imapHost = "imap."+imapHost;
    }

    public Set<String> getSchema() {
        return schema;
    }

    public void setSchema(Set<String> schema) {
        this.schema = schema;
    }

}
