package com.vmo.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false, length = 20, name = "firstName")
    private String firstName;
    @Column(nullable = false, length = 20, name = "lastName")
    private String lastName;
    @Column(unique = true, nullable = false, length = 20, name = "userName")
    private String userName;
    @Column(nullable = false, length = 100, name = "password")
    private String password;
    @Column(unique = true, nullable = false, length = 30, name = "email")
    private String email;
    @Column(unique = true, nullable = false, length = 20, name = "phone")
    private String phone;
    @Column(name = "lastLogin")
    private Date lastLogin;
    @Column(name = "avatarPath")
    private String avatarPath;
    @Column(name = "avatarUrl")
    private String avatarUrl;
    @CreationTimestamp
    @Column(name = "createdAt")
    private Date createAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;
    @Column(name = "isDeleted")
    private boolean isDeleted = false;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_departments",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "departmentId"))
    private List<Department> departments;

    @OneToMany(mappedBy = "user")
    private List<Family> families;
}
