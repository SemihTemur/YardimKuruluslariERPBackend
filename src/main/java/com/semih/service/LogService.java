package com.semih.service;

import com.semih.dto.response.LogResponse;
import com.semih.model.Log;
import com.semih.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    private LogResponse mapToResponse(Log log) {
        return new LogResponse(
                log.getId(),
                log.getCreatedDate(),
                log.getModifiedDate(),
                log.getActionType(),
                log.getTargetEntity(),
                log.getPerformedByUser()
        );
    }

    public List<LogResponse> getLogs() {
        return logRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
