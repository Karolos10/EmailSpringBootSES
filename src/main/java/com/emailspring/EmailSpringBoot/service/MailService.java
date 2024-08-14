package com.emailspring.EmailSpringBoot.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

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
        context.setVariable("domain", "http://karlos.org");
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

    public void sentTemplate(){
        Destination destination = new Destination().withToAddresses(MY_EMAIL);

        Map<String, String> data = new HashMap<>();
        data.put("name", "Carlos Miguel Rodriguez");
        data.put("domain", "http://karlos.org");

        try{
            String templaData = new ObjectMapper().writeValueAsString(data);

            SendTemplatedEmailRequest emailRequest = new SendTemplatedEmailRequest().withSource(MY_EMAIL)
                    .withDestination(destination)
                    .withTemplate("MyTemplateSES")
                            .withTemplateData(templaData);

            client.sendTemplatedEmail(emailRequest);
        }catch (JsonProcessingException e){
            throw  new RuntimeException();
        }
    }
}
