package com.emailspring.EmailSpringBoot.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class MailService {

    private final static String MY_EMAIL = System.getenv("EMAIL");

    @Autowired
    private AmazonSimpleEmailServiceClient client;

    @Autowired
    private TemplateEngine templateEngine;

    public void senHandle(){
        Destination destination = new Destination().withToAddresses(MY_EMAIL);

        Message message = new Message().withSubject(new Content("Prueba de email"))
                .withBody(new Body(new Content("Hola mundo como estas")));

        SendEmailRequest emailRequest = new SendEmailRequest().withSource(MY_EMAIL)
                .withDestination(destination)
                .withMessage(message);

        client.sendEmail(emailRequest);
    }
}
