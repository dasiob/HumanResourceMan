package com.vmo.models.response;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private List<DepartmentDto> departmentDtos;
}
