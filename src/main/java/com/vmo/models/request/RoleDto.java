package com.vmo.models.request;

import com.vmo.common.enums.RoleNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private int roleId;
    private RoleNames roleNames;
}
