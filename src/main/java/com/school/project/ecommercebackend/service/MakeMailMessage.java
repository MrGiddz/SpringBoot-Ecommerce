package com.school.project.ecommercebackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public class MakeMailMessage extends MailMessage{


    /** The from address to use on emails. */
    @Value("${email.from}")
    private String fromAddress;


    /**
     * Makes a SimpleMailMessage for sending.
     * @return The SimpleMailMessage created.
     */
    public SimpleMailMessage makeMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    @Override
    public void sendMail() {}

}
