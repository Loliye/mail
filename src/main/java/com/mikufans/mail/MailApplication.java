package com.mikufans.mail;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@DubboComponentScan(basePackages = "com.mikufans.mail.service.impl")
public class MailApplication
{

    private static Logger log = LoggerFactory.getLogger(MailApplication.class);

    public static void main(String[] args)
    {
        SpringApplication.run(MailApplication.class, args);
        log.info("邮件服务启动");
    }

}
