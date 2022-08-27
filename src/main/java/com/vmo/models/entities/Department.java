package com.vmo.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmo.common.enums.DepartmentNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;
    @Column(unique = true, nullable = false, length = 20, name = "departmentName")
    @Enumerated(EnumType.STRING)
    private DepartmentNames departmentName;
    @CreationTimestamp
    @Column(name = "createdAt")
    private Date createAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;
    @Column(name = "isDeleted")
    private boolean isDeleted = false;

    @ManyToMany(mappedBy = "departments")
    private List<User> users;

    public Department(DepartmentNames departmentNames) {
        super();
        this.departmentName = departmentNames;
    }
}
