package com.mikufans.mail.common.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendMail
{
    @Scheduled(cron = "*/6 * * * * ?")
    public void sendMail()
    {
        System.out.println("邮件发送");
    }
}
