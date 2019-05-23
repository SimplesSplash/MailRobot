/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.mailRobot;

import java.util.HashMap;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author alex1
 */
@Component
public class SimpleMailParser implements MailParser{

    @Override
    public HashMap<String,Object> parseMessage(String text,Set<String> schema) {
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
                return message;
    }
    
}
