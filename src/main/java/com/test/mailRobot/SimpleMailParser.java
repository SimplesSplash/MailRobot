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

    /**
     *Метод находит в тексте ключ, определяет начальный и конечный символ значения ключа,
     * получает подстроку со значением и вырезает из текста строку вида "ключ:значение\n"
     * @param text Текст сообщения
     * @param schema Набор строковых ключей, для которых робот будет искать значения в тексте сообщения
     * @return HashMap найденных пар ключ:значение
     */
    @Override
    public HashMap<String,Object> parseMessage(String text,Set<String> schema) {
        HashMap<String, Object> message = new HashMap<String, Object>();
                StringBuilder tempText = new StringBuilder(text.trim());
                boolean isLast = false;
                for (String field : schema) {
                    int startChar = tempText.indexOf(field + ":");
                    startChar += field.length() + 1;
                    int endChar = -1;
                    if (tempText.indexOf("\n") > 0) {
                        endChar = tempText.indexOf("\n");
                    } else {
                        endChar = tempText.length();
                        isLast = true;
                    }
                    String value = tempText.substring(startChar, endChar).trim();
                    if (!isLast) {
                        tempText=tempText.delete(startChar, endChar + 1) ;
                    } else {
                        tempText=tempText.delete(startChar, endChar) ;
                    }

                    message.put(field, value);
                }
                return message;
    }
    
}
