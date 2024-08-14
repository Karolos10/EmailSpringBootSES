package com.emailspring.EmailSpringBoot.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

    private final static String MY_EMAIL = System.getenv("EMAIL");

    @Autowired
    private AmazonSimpleEmailService client;

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

    public void sendHtml(){
        Destination destination = new Destination().withToAddresses(MY_EMAIL);

        Context context = new Context();
        context.setVariable("name", "Karlos");
        context.setVariable("domain", "Http://karlos.org");
        String html = templateEngine.process("Hello", context);

        Content htmlContent = new Content().withData(html);
        Content textContent = new Content().withData("Version en texto plano");
        Body body = new Body().withHtml(htmlContent).withText(textContent);

        Message message = new Message().withSubject(new Content("Prueba de HTML"))
                .withBody(body);

        SendEmailRequest emailRequest = new SendEmailRequest().withSource(MY_EMAIL)
                .withDestination(destination)
                .withMessage(message);

        client.sendEmail(emailRequest);
    }
}
