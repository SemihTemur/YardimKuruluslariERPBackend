package com.semih.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record LogResponse(
        Long id,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "Europe/Istanbul")
        LocalDateTime createdDate,
        @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "Europe/Istanbul")
        LocalDateTime modifiedDate,
        String actionType,
        String targetEntity,
        String performedByUser) {
}
