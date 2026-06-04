package com.huce.doan.mxh.service;

import jakarta.mail.MessagingException;
import java.util.Map;

public interface MailService {
    void sendMail(Map<String, Object> props, String mail, String template, String subject) throws MessagingException;
}
