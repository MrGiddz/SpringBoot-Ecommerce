package com.school.project.ecommercebackend.service;

import org.springframework.mail.SimpleMailMessage;

public abstract class MailMessage {

    public abstract SimpleMailMessage makeMailMessage();

    public abstract void sendMail();
}
