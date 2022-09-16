package com.vmo.repository;

import com.vmo.models.entities.LeavePlanning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LeavePlanningRepository extends JpaRepository<LeavePlanning, Integer> {

    @Transactional
    @Query(nativeQuery = true, value = "select * from leave_planning c where c.user_id = ?1")
    List<LeavePlanning> findAllByUser(int userId);
}
