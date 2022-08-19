package com.vmo.models.response;

import com.vmo.common.enums.DepartmentNames;
import lombok.Data;

@Data
public class DepartmentDto {
    private int departmentId;
    private DepartmentNames departmentName;
}
