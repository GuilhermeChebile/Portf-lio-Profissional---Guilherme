package com.guilherme.portfolio.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilherme.portfolio.config.ContactInfo;
import com.guilherme.portfolio.model.ContactForm;

/**
 * Envia a mensagem do formulário.
 * - Se RESEND_API_KEY estiver definida, usa a API HTTP do Resend (porta 443). É o modo usado no Render,
 *   cujo plano gratuito bloqueia as portas SMTP.
 * - Caso contrário, usa SMTP (Gmail), que funciona no computador local.
 */
@Service
public class MailService {

    private final ObjectProvider<JavaMailSender> mailSender;
    private final ContactInfo contactInfo;
    private final ObjectMapper mapper;
    private final String smtpFrom;
    private final String resendKey;
    private final String resendFrom;
    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public MailService(ObjectProvider<JavaMailSender> mailSender,
                       ContactInfo contactInfo,
                       ObjectMapper mapper,
                       @Value("${spring.mail.username:}") String smtpFrom,
                       @Value("${RESEND_API_KEY:}") String resendKey,
                       @Value("${RESEND_FROM:onboarding@resend.dev}") String resendFrom) {
        this.mailSender = mailSender;
        this.contactInfo = contactInfo;
        this.mapper = mapper;
        this.smtpFrom = smtpFrom;
        this.resendKey = resendKey;
        this.resendFrom = resendFrom;
    }

    /** Envia a mensagem. Lança exceção se o envio falhar. */
    public void send(ContactForm form) throws Exception {
        if (!resendKey.isBlank()) {
            sendWithResend(form);
        } else {
            sendWithSmtp(form);
        }
    }

    private String subject(ContactForm form) {
        return "[Portfólio] Mensagem de " + form.getName().replaceAll("[\\r\\n]+", " ");
    }

    private String body(ContactForm form) {
        return "Nome: " + form.getName() + "\nE-mail: " + form.getEmail() + "\n\n" + form.getMessage();
    }

    private void sendWithResend(ContactForm form) throws Exception {
        Map<String, Object> payload = Map.of(
                "from", "Portfólio <" + resendFrom + ">",
                "to", List.of(contactInfo.email()),
                "reply_to", form.getEmail(),
                "subject", subject(form),
                "text", body(form));

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.resend.com/emails"))
                .timeout(Duration.ofSeconds(15))
                .header("Authorization", "Bearer " + resendKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new IllegalStateException("Resend respondeu " + response.statusCode() + ": " + response.body());
        }
    }

    private void sendWithSmtp(ContactForm form) {
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null || smtpFrom.isBlank()) {
            throw new IllegalStateException("Nenhum envio configurado (defina RESEND_API_KEY, ou MAIL_USERNAME e MAIL_PASSWORD).");
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(smtpFrom);
        msg.setTo(contactInfo.email());
        msg.setReplyTo(form.getEmail());
        msg.setSubject(subject(form));
        msg.setText(body(form));
        sender.send(msg);
    }
}