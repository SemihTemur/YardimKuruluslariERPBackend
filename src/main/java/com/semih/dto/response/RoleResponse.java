package com.semih.dto.response;

public record RoleResponse(
        BaseResponse baseResponse,
        String roleName
) {
}
