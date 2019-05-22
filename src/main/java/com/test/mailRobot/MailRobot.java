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

import org.springframework.stereotype.Component;

/**
 *
 * @author Валерия
 */
@Component
public class MailRobot {

    private String subject;
    private String address;
    private String password;
    private String imapHost;
    private Set<String> schema;

    public MailRobot() {
    }

    public List<HashMap<String, Object>> getMessages() throws MessagingException, IOException {
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

    public List<HashMap<String, Object>> parseMessages( List<Message> messages) throws MessagingException, IOException {
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (Message m : messages) {
            if (subject.equals(m.getSubject())) {
                Multipart mpmessage = (Multipart) m.getContent();
                BodyPart bp = mpmessage.getBodyPart(0);
                String text = bp.getContent().toString();
                HashMap<String, Object> message = new HashMap<String, Object>();
                boolean isLast = false;
                for (String field : schema) {
                    int startChar = text.indexOf(field + ":");
                    startChar += field.length() + 1;
                    int endChar = -1;
                    if (text.indexOf("\n") > 0) {
                        endChar = text.indexOf("\n");
                    } else {
                        endChar = text.length();
                        isLast = true;
                    }
                    String value = text.substring(startChar, endChar);
                    if (!isLast) {
                        text = text.substring(endChar + 1);
                    } else {
                        text = text.substring(endChar);
                    }

                    message.put(field, value);
                }

                result.add(message);
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
        this.imapHost = imapHost;
    }

    public Set<String> getSchema() {
        return schema;
    }

    public void setSchema(Set<String> schema) {
        this.schema = schema;
    }

}
