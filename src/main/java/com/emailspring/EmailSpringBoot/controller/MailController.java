package com.emailspring.EmailSpringBoot.controller;

import com.emailspring.EmailSpringBoot.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mail")
public class MailController {

    @Autowired
    public MailService mailService;

    @GetMapping("/hello")
    void hello(){
        mailService.senHandle();
    }

    @GetMapping("/demo")
    void demo(){
        mailService.sendHtml();
    }
}
