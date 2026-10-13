package com.guilherme.portfolio.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.guilherme.portfolio.config.ContactInfo;
import com.guilherme.portfolio.model.ContactForm;

@Service
public class MailService {

    private final ObjectProvider<JavaMailSender> mailSender;
    private final ContactInfo contactInfo;
    private final String from;

    public MailService(ObjectProvider<JavaMailSender> mailSender,
                       ContactInfo contactInfo,
                       @Value("${spring.mail.username:}") String from) {
        this.mailSender = mailSender;
        this.contactInfo = contactInfo;
        this.from = from;
    }

    /** Envia a mensagem do formulário para o e-mail do portfólio. Lança exceção se o SMTP falhar. */
    public void send(ContactForm form) {
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null || from.isBlank()) {
            throw new IllegalStateException("SMTP não configurado (defina MAIL_USERNAME e MAIL_PASSWORD).");
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(contactInfo.email());
        msg.setReplyTo(form.getEmail());
        msg.setSubject("[Portfólio] Mensagem de " + form.getName());
        msg.setText("Nome: " + form.getName() + "\nE-mail: " + form.getEmail() + "\n\n" + form.getMessage());
        sender.send(msg);
    }
}
