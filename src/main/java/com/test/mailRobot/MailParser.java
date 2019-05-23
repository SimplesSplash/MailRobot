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

public interface MailParser {
    public HashMap<String,Object> parseMessage(String text,Set<String> schema);
}
