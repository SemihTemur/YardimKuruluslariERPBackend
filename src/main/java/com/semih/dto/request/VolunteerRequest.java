package com.semih.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerRequest {

    private String name;

    private String surname;

    private String phone;

    private AddressRequest address;

}
