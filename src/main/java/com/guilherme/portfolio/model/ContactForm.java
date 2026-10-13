package com.guilherme.portfolio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactForm {

    @NotBlank(message = "{contact.error.name}")
    @Size(max = 100, message = "{contact.error.name}")
    private String name;

    @NotBlank(message = "{contact.error.email}")
    @Email(message = "{contact.error.email}")
    @Size(max = 150, message = "{contact.error.email}")
    private String email;

    @NotBlank(message = "{contact.error.message}")
    @Size(min = 10, max = 2000, message = "{contact.error.message}")
    private String message;

    private String website;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
