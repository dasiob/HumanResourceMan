package com.vmo.service;

import com.vmo.models.request.FamilyDto;
import com.vmo.models.response.Message;

import java.util.List;

public interface FamilyService {
    List<FamilyDto> getFamilyByUser(int userId);

    FamilyDto addFamily(int userId, FamilyDto familyDto);

    FamilyDto updateFamily(int userId, int familyId, FamilyDto familyDto);

    Message deleteFamily(int familyId);
}
