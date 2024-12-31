package com.semih.dto.response;

import com.semih.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressResponse {

    private String city;

    private String district;

    private String neighborhood;

    private String street;

}
