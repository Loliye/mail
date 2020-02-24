package com.mikufans.mail.service;

import com.mikufans.mail.common.model.Email;
import com.mikufans.mail.common.model.Result;

public interface IMailService
{
    void send(Email email) throws Exception;

    void sendHtml(Email email) throws Exception;

    void sendFreemarker(Email email) throws Exception;

    void sendThymeleaf(Email email) throws Exception;

    void sendQueue(Email email) throws Exception;

    void sendRedisQueue(Email email) throws Exception;

    Result listMail(Email email);
}
