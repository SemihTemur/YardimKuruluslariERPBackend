package com.semih.dto.request;

import com.semih.enums.UserType;

public record AddressRequest(String city,
                             String district,
                             String neighborhood,
                             String street,
                             UserType userType
) {

}
