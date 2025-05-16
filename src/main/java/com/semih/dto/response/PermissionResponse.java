package com.semih.dto.response;

import com.semih.enums.ActionType;
import com.semih.enums.EntityName;

public record PermissionResponse(BaseResponse baseResponse, String roleName,
                                 EntityName entityName, ActionType actionType) {
}
