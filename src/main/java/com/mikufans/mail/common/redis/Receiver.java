package com.mikufans.mail.common.redis;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikufans.mail.common.model.Email;
import com.mikufans.mail.service.IMailService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Receiver
{
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Reference(check = false)
    private IMailService mailService;

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch)
    {
        this.latch = latch;
    }

    public void receiverMessage(String message)
    {
        logger.info("接受email消息<{}>", message);
        if (message == null)
            logger.info("接收到email消息<null>");
        else
        {
            ObjectMapper mapper = new ObjectMapper();
            try
            {
                Email email = mapper.readValue(message, Email.class);
                mailService.send(email);
                logger.info("接受email消息内容<{}>", email.getContent());
            } catch (JsonParseException e)
            {
                e.printStackTrace();
            } catch (JsonMappingException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        latch.countDown();
    }

}
