package com.mikufans.mail.web;

import com.mikufans.mail.common.model.Email;
import com.mikufans.mail.common.model.Result;
import com.mikufans.mail.service.IMailService;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "邮件管理")
@RestController
@RequestMapping("/mail")
public class MailController
{
    @Reference(version = "1.0.0")
    private IMailService mailService;

    @PostMapping("/send")
    public Result send(Email email)
    {
        try
        {
            mailService.sendFreemarker(email);
        } catch (Exception e)
        {
            e.printStackTrace();
            return Result.error();
        }
        return Result.ok();
    }

    @PostMapping("/list")
    public Result list(Email email)
    {
        return mailService.listMail(email);
    }
}
