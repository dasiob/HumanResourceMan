package com.vmo.models.response;

import com.vmo.common.enums.PlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavePlanningResponse {
    private int planId;
    private String type;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private PlanStatus status;
    private int userId;
}
