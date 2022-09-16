package com.vmo.models.request;

import com.vmo.common.enums.PlanStatus;
import com.vmo.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavePlanningDto {
    private int planId;
    private boolean type;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private PlanStatus status;
    private User user;
}
