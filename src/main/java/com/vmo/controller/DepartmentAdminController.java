package com.vmo.controller;

import com.vmo.models.request.DepartmentDto;
import com.vmo.models.response.DepartmentPagingResponse;
import com.vmo.service.DepartmentAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departmentAdmin")
public class DepartmentAdminController {

    @Autowired
    private DepartmentAdminService departmentAdminService;

    @GetMapping("/listDepartments")
    public DepartmentPagingResponse getAllDepartments(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return departmentAdminService.getAllDepartments(pageNo, pageSize);
    }

    @PostMapping("/addDepartment")
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentAdminService.createDepartment(departmentDto);
    }

    @PutMapping("/updateDepartment/{departmentId}")
    public DepartmentDto updateDepartment(@PathVariable int departmentId, @RequestBody DepartmentDto departmentDto) {
        return departmentAdminService.updateDepartment(departmentId, departmentDto);
    }

    @DeleteMapping("/deleteDepartment/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int departmentId) {
        return ResponseEntity.ok(departmentAdminService.deleteDepartment(departmentId));
    }
}
