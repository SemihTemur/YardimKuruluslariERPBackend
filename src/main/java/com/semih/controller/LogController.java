package com.semih.controller;

import com.semih.dto.response.LogResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs")
    public ResponseEntity<RestResponse<List<LogResponse>>> getLog() {
        List<LogResponse> logs = logService.getLogs();
        return new ResponseEntity<>(RestResponse.of(logs), HttpStatus.OK);
    }
}
