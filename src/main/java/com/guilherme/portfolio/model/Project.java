package com.guilherme.portfolio.model;

import java.util.List;

public record Project(String key, List<String> technologies, String repositoryUrl, String image, boolean ongoing) {
}
