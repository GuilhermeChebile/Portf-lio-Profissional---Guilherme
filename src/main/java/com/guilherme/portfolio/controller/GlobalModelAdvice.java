package com.guilherme.portfolio.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.guilherme.portfolio.config.ContactInfo;

/** Disponibiliza os dados de contato (rodapé e página de contato) em todas as views. */
@ControllerAdvice
public class GlobalModelAdvice {

    private final ContactInfo contactInfo;

    public GlobalModelAdvice(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @ModelAttribute("contact")
    public ContactInfo contact() {
        return contactInfo;
    }
}
