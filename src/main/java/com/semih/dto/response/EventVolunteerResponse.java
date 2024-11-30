package com.semih.dto.response;

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
public class EventVolunteerResponse {

    private EventResponse event;

    private VolunteerResponse volunteer;

    private VolunteerRole role;

    private LocalDate participationDateVolunteer;
}
