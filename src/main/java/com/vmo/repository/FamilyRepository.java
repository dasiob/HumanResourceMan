package com.vmo.repository;

import com.vmo.models.entities.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer> {

    @Transactional
    @Query(nativeQuery = true, value = "select * from families c where c.user_id = ?1")
    List<Family> findAllByUser(int userId);
}
