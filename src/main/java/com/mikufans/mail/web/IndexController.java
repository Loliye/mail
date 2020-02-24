package com.mikufans.mail.web;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "通用访问拦截匹配")
@Controller
public class IndexController
{
    @GetMapping("{url}.shtml")
    public String page(@PathVariable("url") String url)
    {
        return url;
    }

    @GetMapping("{module}/{url}.shtml")
    public String page(@PathVariable("module") String module, @PathVariable("url") String url)
    {
        return module + "/" + url;
    }

}
