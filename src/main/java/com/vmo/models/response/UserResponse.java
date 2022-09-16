package com.vmo.models.response;

import com.vmo.models.request.DepartmentDto;
import com.vmo.models.request.FamilyDto;
import com.vmo.models.request.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String avatarUrl;
    private String avatarPath;
    private String email;
    private String phone;
    private List<FamilyDto> familyDtos;
    private List<DepartmentDto> departmentDtos;
    private List<RoleDto> roleDtos;
}
