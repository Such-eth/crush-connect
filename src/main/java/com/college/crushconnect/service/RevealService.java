package com.college.crushconnect.service;

import com.college.crushconnect.repository.AppConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class RevealService {

    private static final String KEY = "reveal_enabled";

    private final AppConfigRepository repo;

    public RevealService(AppConfigRepository repo) {
        this.repo = repo;
    }

    public boolean isRevealEnabled() {
        return repo.findById(KEY)
                .map(cfg -> Boolean.parseBoolean(cfg.getValue()))
                .orElse(false);
    }
}
