package com.vmo.service;

import com.vmo.models.request.LeavePlanningDto;
import com.vmo.models.response.LeavePlanningResponse;
import com.vmo.models.response.Message;

import java.util.List;

public interface LeavePlanningService {
    List<LeavePlanningResponse> getLeavePlanningListAdmin();
    List<LeavePlanningResponse> getLeavePlanningListUser(int userId);
    LeavePlanningResponse addLeavePlan(LeavePlanningDto leavePlanningDto, int userId);
    Message updateLeavePlanByUser(LeavePlanningDto leavePlanningDto, int planId, int userId);
    Message updateLeavePlanByAdmin(LeavePlanningDto leavePlanningDto, int planId);
    Message deleteLeavePlan(int planId);
    //userId is belong to user that got queried not the admin
    Message getTotalLeaveDaysOfAUser(int userId, int year);
}
