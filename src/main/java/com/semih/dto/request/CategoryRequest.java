package com.semih.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {

//    @NotBlank(message = "categoryName cannot be null or empty")
//    @Size(min = 3, message = "categoryName must be at least 3 characters long")
    private String categoryName;

}
