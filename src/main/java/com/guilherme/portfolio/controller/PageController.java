package com.guilherme.portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guilherme.portfolio.model.ContactForm;
import com.guilherme.portfolio.service.MailService;
import com.guilherme.portfolio.service.PortfolioService;

@Controller
public class PageController {

    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    private final PortfolioService portfolio;
    private final MailService mailService;

    public PageController(PortfolioService portfolio, MailService mailService) {
        this.portfolio = portfolio;
        this.mailService = mailService;
    }

    @GetMapping("/")
    public String about(Model model) {
        model.addAttribute("active", "about");
        model.addAttribute("age", portfolio.age());
        model.addAttribute("education", portfolio.education());
        model.addAttribute("skills", portfolio.skills());
        return "index";
    }

    @GetMapping("/projetos")
    public String projects(Model model) {
        model.addAttribute("active", "projects");
        model.addAttribute("projects", portfolio.projects());
        return "projetos";
    }

    @GetMapping("/experiencias")
    public String experiences(Model model) {
        model.addAttribute("active", "experience");
        model.addAttribute("experiences", portfolio.experiences());
        return "experiencias";
    }

    @GetMapping("/contato")
    public String contact(Model model) {
        model.addAttribute("active", "contact");
        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactForm());
        }
        return "contato";
    }

    @PostMapping("/contato")
    public String sendContact(@Validated @ModelAttribute("contactForm") ContactForm form,
                              BindingResult result,
                              RedirectAttributes redirect,
                              Model model) {
        model.addAttribute("active", "contact");
        if (result.hasErrors()) {
            return "contato";
        }
        if (form.getWebsite() != null && !form.getWebsite().isBlank()) {
            redirect.addFlashAttribute("sent", true);
            return "redirect:/contato";
        }
        try {
            mailService.send(form);
            redirect.addFlashAttribute("sent", true);
        } catch (Exception e) {
            log.error("Falha ao enviar e-mail de contato", e);
            redirect.addFlashAttribute("failed", true);
            redirect.addFlashAttribute("contactForm", form);
        }
        return "redirect:/contato";
    }
}
