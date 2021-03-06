package com.mikufans.mail.common.queue;

import com.mikufans.mail.common.model.Email;
import com.mikufans.mail.service.IMailService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ConsumeMailQueue
{
    private static final Logger logger = LoggerFactory.getLogger(ConsumeMailQueue.class);

    @Reference(check = false)
    IMailService mailService;

    @PostConstruct
    public void startThread()
    {
        ExecutorService e = Executors.newFixedThreadPool(2);
        e.submit(new PollMail(mailService));
        e.submit(new PollMail(mailService));
    }

    @PreDestroy
    public void stopThread()
    {
        logger.info("destroy");
    }

    class PollMail implements Runnable
    {
        IMailService mailService;

        public PollMail(IMailService mailService)
        {
            this.mailService = mailService;
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Email email = MailQueue.getMailQueue().consume();
                    if (email != null)
                    {
                        logger.info("剩余邮件总数{}", MailQueue.getMailQueue().size());
                        mailService.send(email);
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
