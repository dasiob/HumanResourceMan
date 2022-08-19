package com.vmo.models.entities;

import com.vmo.common.enums.RoleNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column(unique = true, nullable = false, length = 20, name = "roleName")
    @Enumerated(EnumType.STRING)
    private RoleNames roleName;
    @CreationTimestamp
    @Column(name = "createdAt")
    private Date createAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;
    @Column(name = "isDeleted")
    private boolean isDeleted = false;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
