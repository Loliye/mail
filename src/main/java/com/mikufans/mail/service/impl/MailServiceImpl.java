package com.mikufans.mail.service.impl;

import com.mikufans.mail.common.dynamicquery.DynamicQuery;
import com.mikufans.mail.common.model.Email;
import com.mikufans.mail.common.model.Result;
import com.mikufans.mail.common.queue.MailQueue;
import com.mikufans.mail.common.util.Constants;
import com.mikufans.mail.entity.OaEmail;
import com.mikufans.mail.repostiory.MailRepository;
import com.mikufans.mail.service.IMailService;
import com.sun.xml.internal.ws.encoding.HasEncoding;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
public class MailServiceImpl implements IMailService
{
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    static
    {
        System.setProperty("mail.mime,splitlongparameters", "false");
    }

    @Autowired
    public Configuration configuration;
    @Value("${spring.mail.username}")
    public String USER_NAME;
    @Value("${server.path}")
    public String PATH;
    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void send(Email email) throws Exception
    {
        logger.info("发送邮件：{}", email.getContent());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USER_NAME);
        message.setTo(email.getEmail());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        mailSender.send(message);
    }

    @Override
    public void sendHtml(Email email) throws Exception
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(USER_NAME, "java笔记");
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        //添加附加
        helper.setText("<html><body><img src=\"cid:springcloud\" ></body></html>", true);
        File file = ResourceUtils.getFile("classpath:static" + Constants.SF_FILE_SEPARATOR + "image" +
                Constants.SF_FILE_SEPARATOR + "springclound.png");

        file = ResourceUtils.getFile("classpath:static"
                + Constants.SF_FILE_SEPARATOR + "file"
                + Constants.SF_FILE_SEPARATOR + "关注科帮网获取更多源码.zip");

        helper.addAttachment("学习资料",file);
        mailSender.send(message);

    }

    @Override
    public void sendFreemarker(Email email) throws Exception
    {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        helper.setFrom(USER_NAME, "java笔记");
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        Map<String,Object> model=new HashMap<>();
        model.put("mail",email);
        model.put("path",PATH);

        Template template=configuration.getTemplate(email.getTemplate());
        String text= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);

        helper.setText(text,true);
        mailSender.send(message);
        email.setContent(text);
        OaEmail oaEmail=new OaEmail(email);
        mailRepository.save(oaEmail);
    }

    //弃用
    @Override
    public void sendThymeleaf(Email email) throws Exception
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(USER_NAME);
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        Context context = new Context();
        context.setVariable("email", email);
        String text = templateEngine.process(email.getTemplate(), context);
        helper.setText(text, true);
        mailSender.send(message);
    }

    @Override
    public void sendQueue(Email email) throws Exception
    {
        MailQueue.getMailQueue().produce(email);
    }

    @Override
    public void sendRedisQueue(Email email) throws Exception
    {
        redisTemplate.convertAndSend("mail",email);
    }

    @Override
    public Result listMail(Email email)
    {
        List<OaEmail> list =mailRepository.findAll();
        return Result.ok(list);
    }
}
