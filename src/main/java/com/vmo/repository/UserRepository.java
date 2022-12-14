package com.vmo.repository;

import com.vmo.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByuserName(String userName);
    Optional<User> findByemail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.isDeleted=false WHERE a.email=?1")
    void enableUser(String email);
}
