package com.vmo.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDto {
    private int familyId;
    private String firstName;
    private String lastName;
    private String relationship;
    private String phone;
    private int userId;
}
