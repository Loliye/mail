package com.mikufans.mail.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MailUtil
{
    private final AtomicInteger count = new AtomicInteger();
    private Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

    public void start(final JavaMailSender mailSender, final SimpleMailMessage message)
    {
        service.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if (count.get() == 2)
                    {
                        service.shutdown();
                        logger.info("the task is down");
                    }
                    logger.info("start send email and the index is " + count);
                    mailSender.send(message);
                    logger.info("send email success");
                } catch (Exception e)
                {
                    logger.error("Send email fail ", e);
                }
            }
        });
    }

    public void startHtml(final JavaMailSender mailSender, final MimeMessage message)
    {
        service.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    if (count.get() == 2)
                    {
                        service.shutdown();
                        logger.info("the task is down");
                    }
                    logger.info("start send email and the index is " + count);
                    mailSender.send(message);
                    logger.info("send email success");
                } catch (Exception e)
                {
                    logger.error("send email fail", e);
                }

            }
        });
    }

    public static JavaMailSenderImpl createMailSender()
    {
        JavaMailSenderImpl sender=new JavaMailSenderImpl();
        sender.setHost("smtp.qq.com");
        sender.setUsername("1448672486@qq.com");
        sender.setDefaultEncoding("utf-8");
        sender.setPassword("utmgrpcnddabbach");

        Properties p=new Properties();
        p.setProperty("mail.smtp.timeout",1000+"");
        p.setProperty("mail.smtp.auth","true");
        sender.setJavaMailProperties(p);
        return sender;
    }


}
