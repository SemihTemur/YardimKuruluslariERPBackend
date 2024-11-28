package com.semih.dto.request;

import com.semih.enums.VolunteerRole;
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

    private EventRequest event;

    private VolunteerRequest volunteer;

    private VolunteerRole role;

}
