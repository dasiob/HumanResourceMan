package com.vmo.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int familyId;
    @Column(nullable = false, length = 20, name = "firstName")
    private String firstName;
    @Column(nullable = false, length = 20, name = "lastName")
    private String lastName;
    @Column(nullable = false, name = "relationship")
    private String relationship;
    @Column(unique = true, nullable = false, length = 20, name = "phone")
    private String phone;
    @CreationTimestamp
    @Column(name = "createdAt")
    private Date createAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;
    @Column(name = "isDeleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(nullable = false,
               name = "userId")
    private User user;
}
