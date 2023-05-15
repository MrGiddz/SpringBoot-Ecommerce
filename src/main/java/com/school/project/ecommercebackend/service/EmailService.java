package com.school.project.ecommercebackend.service;

import com.school.project.ecommercebackend.exception.EmailFailureException;
import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
@Service
public class EmailService extends MakeMailMessage {

    /** The url of the front end for links. */
    @Value("${app.frontend.url}")
    private String url;
    /** The JavaMailSender instance. */
//    private final JavaMailSenderImpl javaMailSenderImpl;

    private final JavaMailSender javaMailSender;


    /**
     * Constructor for spring injection.
     *
//     * @param javaMailSenderImpl JavaMailSenderImpl
     * @param javaMailSender     JavaMailSender
     */
    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSenderImpl = javaMailSenderImpl;
        this.javaMailSender = javaMailSender;
    }





    /**
     * Sends a verification email to the user.
     * @param verificationToken The verification token to be sent.
     * @throws EmailFailureException Thrown if are unable to send the email.
     */
    public void sendMail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Verify your email to active your account.");
        message.setText("Please follow the link below to verify your email to active your account.\n" +
                url + "/auth/verify?token=" + verificationToken.getToken());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }

    /**
     * Sends a password reset request email to the user.
     * @param user The user to send to.
     * @param token The token to send the user for reset.
     * @throws EmailFailureException Exception
     */
    public void sendMail(LocalUser user, String token) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Your password reset request link.");
        message.setText("You requested a password reset on our website. Please " +
                "find the link below to be able to reset your password.\n" + url +
                "/auth/reset?token=" + token);
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }

}
