package com.semih.dto.request;

import com.semih.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequest {

    private String city;

    private String district;

    private String neighborhood;

    private String street;

    private UserType userType;

}
