package com.vmo.service;

import com.vmo.models.request.DepartmentDto;
import com.vmo.models.response.DepartmentPagingResponse;
import com.vmo.models.response.Message;

public interface DepartmentAdminService {

    DepartmentPagingResponse getAllDepartments(int pageNo, int pageSize);
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto updateDepartment(int departmentId, DepartmentDto departmentDto);
    Message deleteDepartment(int departmentId);
}
