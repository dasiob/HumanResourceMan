package com.vmo.controller;

import com.vmo.models.request.FamilyDto;
import com.vmo.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @GetMapping("/familyList/{userId}")
    public ResponseEntity<?> getFamilyByUser(@PathVariable int userId) {
        return ResponseEntity.ok(familyService.getFamilyByUser(userId));
    }

    @PostMapping("/addFamily/{userId}")
    public ResponseEntity<?> addFamily(@RequestBody FamilyDto familyDto, @PathVariable int userId) {
        return ResponseEntity.ok(familyService.addFamily(userId, familyDto));
    }

    @PutMapping("/updateFamily")
    public ResponseEntity<?> updateFamily(@RequestParam int userId, @RequestParam int familyId, @RequestBody FamilyDto familyDto) {
        return ResponseEntity.ok(familyService.updateFamily(userId, familyId, familyDto));
    }

    @DeleteMapping("/deleteFamily/{familyId}")
    public ResponseEntity<?> deleteFamily(@PathVariable int familyId) {
        return ResponseEntity.ok(familyService.deleteFamily(familyId));
    }
}
