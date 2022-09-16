package com.vmo.service.implementation;

import com.vmo.common.config.MapperUtil;
import com.vmo.models.entities.Family;
import com.vmo.models.entities.User;
import com.vmo.models.request.FamilyDto;
import com.vmo.models.response.Message;
import com.vmo.repository.FamilyRepository;
import com.vmo.repository.UserRepository;
import com.vmo.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<FamilyDto> getFamilyByUser(int userId) {
        return MapperUtil.mapList(familyRepository.findAllByUser(userId), FamilyDto.class);
    }

    @Override
    public FamilyDto addFamily(int userId, FamilyDto familyDto) {
        User user = userRepository.findById(userId).get();

        familyDto.setUserId(userId);
        Family family = MapperUtil.map(familyDto, Family.class);
        family.setUser(user);
        familyRepository.save(family);

        return MapperUtil.map(family, FamilyDto.class);
    }

    @Override
    public FamilyDto updateFamily(int userId, int familyId, FamilyDto familyDto) {
        User user = userRepository.findById(userId).get();
        Family family = familyRepository.findById(familyId).get();

        familyDto.setUserId(userId);
        family.setFirstName(familyDto.getFirstName());
        family.setLastName(familyDto.getLastName());
        family.setPhone(familyDto.getPhone());
        family.setRelationship(familyDto.getRelationship());
        family.setUser(user);
        familyRepository.save(family);

        return MapperUtil.map(family, FamilyDto.class);
    }

    @Override
    public Message deleteFamily(int familyId) {
        Family family = familyRepository.findById(familyId).get();
        family.setDeleted(true);
        return new Message("This family member has been removed");
    }
}
