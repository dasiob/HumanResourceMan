package com.vmo.models.response;

import com.vmo.common.enums.DepartmentNames;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDto {
    private int departmentId;
    private DepartmentNames departmentName;

    public DepartmentDto(DepartmentNames departmentName) {
        super();
        this.departmentName = departmentName;
    }
}
