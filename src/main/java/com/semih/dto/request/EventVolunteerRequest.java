package com.semih.dto.request;

import com.semih.enums.VolunteerRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventVolunteerRequest {

//    @NotNull(message = "event cannot be null")
    private EventRequest event;
//
//    @NotNull(message = "volunteer cannot be null")
    private VolunteerRequest volunteer;
//
//    @NotNull(message = "role cannot be null")
    private VolunteerRole role;

}
