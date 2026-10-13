package com.guilherme.portfolio.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

import com.guilherme.portfolio.model.Education;
import com.guilherme.portfolio.model.Experience;
import com.guilherme.portfolio.model.Project;

/** Fonte dos dados do portfólio. Os textos traduzidos ficam nos arquivos messages*.properties. */
@Service
public class PortfolioService {

    private static final LocalDate BIRTH_DATE = LocalDate.of(2008, 1, 25);

    public int age() {
        return Period.between(BIRTH_DATE, LocalDate.now()).getYears();
    }

    /** Do mais antigo ao mais recente, como pede o enunciado. */
    public List<Project> projects() {
        return List.of(
                new Project("collecty",
                        List.of("Python", "Pygame"),
                        "https://github.com/Vitor-vas/CollectGame_Python.git",
                        "/img/collecty-game.jpg",
                        false),
                new Project("portoes",
                        List.of("Java", "Thymeleaf"),
                        "https://github.com/leopettersen/TP-sistema-hospitalar.git",
                        "/img/portoes-do-ceu.jpg",
                        true));
    }

    public List<Experience> experiences() {
        return List.of(new Experience("marketing",
                List.of("meta", "google", "seo", "social", "sites")));
    }

    public List<Education> education() {
        return List.of(new Education("magnum"), new Education("batista"), new Education("puc"));
    }

    public List<String> skills() {
        return List.of("Java", "Spring Boot", "Thymeleaf", "Python", "Pygame", "HTML/CSS/JS",
                "Meta Ads", "Google Ads", "SEO");
    }
}
