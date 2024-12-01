package com.semih.service;

import com.semih.repository.AidRepository;
import org.springframework.stereotype.Service;

@Service
public class AidService {

    private final AidRepository aidRepository;

    public AidService(AidRepository aidRepository) {
        this.aidRepository = aidRepository;
    }
}
