package com.vmo.service.implementation;

import com.vmo.common.config.MapperUtil;
import com.vmo.models.entities.Department;
import com.vmo.models.request.DepartmentDto;
import com.vmo.models.response.DepartmentPagingResponse;
import com.vmo.models.response.Message;
import com.vmo.repository.DepartmentRepository;
import com.vmo.service.DepartmentAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentAdminServiceimpl implements DepartmentAdminService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentPagingResponse getAllDepartments(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Department> departments = departmentRepository.findAll(pageable);
        List<Department> departmentList = departments.getContent();
        List<DepartmentDto> content = MapperUtil.mapList(departmentList, DepartmentDto.class);

        DepartmentPagingResponse departmentPagingResponse = new DepartmentPagingResponse();
        departmentPagingResponse.setContent(content);
        departmentPagingResponse.setPageNo(departments.getNumber());
        departmentPagingResponse.setPageSize(departments.getSize());
        departmentPagingResponse.setTotalElements(departments.getTotalElements());
        departmentPagingResponse.setTotalPages(departments.getTotalPages());
        departmentPagingResponse.setLast(departments.isLast());
        return departmentPagingResponse;
    }

    @Override
    @Transactional
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = MapperUtil.map(departmentDto, Department.class);
        departmentRepository.save(department);
        return MapperUtil.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentDto updateDepartment(int departmentId, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentId).get();
        department.setDepartmentName(departmentDto.getDepartmentName());
        departmentRepository.save(department);
        return MapperUtil.map(department, DepartmentDto.class);
    }

    @Override
    public Message deleteDepartment(int departmentId) {
        Department department = departmentRepository.findById(departmentId).get();
        department.setDeleted(true);
        departmentRepository.save(department);
        return new Message("Department is deleted");
    }
}
