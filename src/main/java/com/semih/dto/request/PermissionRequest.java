package com.semih.dto.request;

import com.semih.enums.ActionType;
import com.semih.enums.EntityName;

public record PermissionRequest(String roleName,
                                EntityName entityName, ActionType actionType) {
}
