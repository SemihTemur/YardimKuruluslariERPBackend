package com.semih.dto.response;

public record RegisterResponse(BaseResponse baseResponse, String username, String email, String password,
                               String roleName) {
}
