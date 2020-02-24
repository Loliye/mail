package com.mikufans.mail.common.model;

import java.util.HashMap;

public class Email
{
    private static final long serialVersionUID = 1L;

    private String[] email;//接收方的邮件

    private String subject;//主题

    private String content;//邮件内容

    private String template;//模板

    private HashMap<String, String> kvMap;


    public Email()
    {
        super();
    }

    public Email(String[] email, String subject, String content, String template,
                 HashMap<String, String> kvMap)
    {
        super();
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.template = template;
        this.kvMap = kvMap;
    }


    public String[] getEmail()
    {
        return email;
    }

    public void setEmail(String[] email)
    {
        this.email = email;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTemplate()
    {
        return template;
    }

    public void setTemplate(String template)
    {
        this.template = template;
    }

    public HashMap<String, String> getKvMap()
    {
        return kvMap;
    }

    public void setKvMap(HashMap<String, String> kvMap)
    {
        this.kvMap = kvMap;
    }
}
