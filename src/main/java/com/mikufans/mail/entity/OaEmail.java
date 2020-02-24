package com.mikufans.mail.entity;

import com.mikufans.mail.common.model.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "oa_email")
public class OaEmail implements Serializable
{
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 接收人邮箱(多个逗号分开)
     */
    private String receiveEmail;

    /**
     * 主题
     */
    private String subject;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 模板
     */
    private String template;

    /**
     * 发送时间
     */
    private Timestamp sendTime;


    public OaEmail()
    {
        super();
    }

    public OaEmail(Email mail)
    {
        this.receiveEmail = Arrays.toString(mail.getEmail());
        this.subject = mail.getSubject();
        this.content = mail.getContent();
        this.template = mail.getTemplate();
        this.sendTime = new Timestamp(new Date().getTime());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "receive_email", nullable = false, length = 500)
    public String getReceiveEmail()
    {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail)
    {
        this.receiveEmail = receiveEmail;
    }

    @Column(name = "subject", nullable = false, length = 100)
    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    @Column(name = "content", nullable = false, length = 65535)
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Column(name = "template", nullable = false, length = 100)
    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(String template)
    {
        this.template = template;
    }

    @Column(name = "send_time", nullable = false, length = 19)
    public Timestamp getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime)
    {
        this.sendTime = sendTime;
    }
}
