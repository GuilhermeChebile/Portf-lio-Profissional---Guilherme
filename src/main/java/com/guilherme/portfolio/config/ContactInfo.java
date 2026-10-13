package com.guilherme.portfolio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Dados de contato, lidos de application.properties (prefixo portfolio.contact). */
@ConfigurationProperties(prefix = "portfolio.contact")
public record ContactInfo(
        String email,
        String whatsappPersonal,
        String whatsappBusiness,
        String phonePersonal,
        String phoneBusiness,
        String linkedin,
        String github) {
}
