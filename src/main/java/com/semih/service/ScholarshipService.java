package com.semih.service;

import com.semih.repository.ScholarshipRepository;
import org.springframework.stereotype.Service;

@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public ScholarshipService(ScholarshipRepository scholarshipRepository) {
        this.scholarshipRepository = scholarshipRepository;
    }
}
