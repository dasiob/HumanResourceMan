package com.vmo.models.entities;

import com.vmo.common.enums.PlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "leave_planning")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeavePlanning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planId;
    //true equals paid leave
    @Column(nullable = false, name = "type")
    private boolean type;
    @Column(nullable = false, name = "fromDate")
    private LocalDate fromDate;
    @Column(nullable = false, name = "toDate")
    private LocalDate toDate;
    @Column(nullable = false, name = "reason")
    private String reason;
    //true means leave plan is verified
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PlanStatus status = PlanStatus.PROCESSING;

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
