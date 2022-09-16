package com.vmo.service.implementation;

import com.vmo.common.config.MapperUtil;
import com.vmo.common.enums.PlanStatus;
import com.vmo.models.entities.LeavePlanning;
import com.vmo.models.entities.User;
import com.vmo.models.request.LeavePlanningDto;
import com.vmo.models.response.LeavePlanningResponse;
import com.vmo.models.response.Message;
import com.vmo.repository.LeavePlanningRepository;
import com.vmo.repository.UserRepository;
import com.vmo.service.LeavePlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LeavePlanningServiceImpl implements LeavePlanningService {

    @Autowired
    private LeavePlanningRepository leavePlanningRepository;
    @Autowired
    private UserRepository userRepository;

    private List<LeavePlanningResponse> convertToPlanResponse(List<LeavePlanning> leavePlanningList) {
        List<LeavePlanningResponse> planningResponseList = new ArrayList<>();
        for (int i = 0; i < leavePlanningList.size(); i++) {
            LeavePlanning plan = leavePlanningList.get(i);
            LeavePlanningResponse planResponse = new LeavePlanningResponse();
            planResponse.setPlanId(plan.getPlanId());
            if (plan.isType()) planResponse.setType("Paid Leave");
            else planResponse.setType("Unpaid Leave");
            planResponse.setFromDate(plan.getFromDate());
            planResponse.setToDate(plan.getToDate());
            planResponse.setReason(plan.getReason());
            planResponse.setStatus(plan.getStatus());
            planResponse.setUserId(plan.getUser().getUserId());
            planningResponseList.add(planResponse);
        }
        return planningResponseList;
    }

    @Override
    public List<LeavePlanningResponse> getLeavePlanningListAdmin() {
        List<LeavePlanning> leavePlanningList = leavePlanningRepository.findAll();
        return convertToPlanResponse(leavePlanningList);
    }

    @Override
    public List<LeavePlanningResponse> getLeavePlanningListUser(int userId) {
        List<LeavePlanning> leavePlanningList = leavePlanningRepository.findAllByUser(userId);
        return convertToPlanResponse(leavePlanningList);
    }

    @Override
    public LeavePlanningResponse addLeavePlan(LeavePlanningDto leavePlanningDto, int userId) {
        LeavePlanningResponse planResponse = new LeavePlanningResponse();
        User user = userRepository.findById(userId).get();
        leavePlanningDto.setUser(user);
        leavePlanningDto.setStatus(PlanStatus.PROCESSING);
        LeavePlanning plan = leavePlanningRepository.save(MapperUtil.map(leavePlanningDto, LeavePlanning.class));
        planResponse.setPlanId(plan.getPlanId());
        if (plan.isType()) planResponse.setType("Paid Leave");
        else planResponse.setType("Unpaid Leave");
        planResponse.setFromDate(plan.getFromDate());
        planResponse.setToDate(plan.getToDate());
        planResponse.setReason(plan.getReason());
        planResponse.setStatus(plan.getStatus());
        planResponse.setUserId(plan.getUser().getUserId());
        return planResponse;
    }

    @Override
    public Message updateLeavePlanByUser(LeavePlanningDto leavePlanningDto, int planId, int userId) {
        User user = userRepository.findById(userId).get();

        LeavePlanning updated_plan = leavePlanningRepository.findById(planId).get();
        updated_plan.setType(leavePlanningDto.isType());
        updated_plan.setFromDate(leavePlanningDto.getFromDate());
        updated_plan.setToDate(leavePlanningDto.getToDate());
        updated_plan.setReason(leavePlanningDto.getReason());
        updated_plan.setStatus(PlanStatus.PROCESSING);
        updated_plan.setUser(user);
        leavePlanningRepository.save(updated_plan);
        return new Message("Your new update plan is submitted successfully");
    }

    @Override
    public Message updateLeavePlanByAdmin(LeavePlanningDto leavePlanningDto, int planId) {
        LeavePlanning leavePlan = leavePlanningRepository.findById(planId).get();
        leavePlan.setStatus(leavePlanningDto.getStatus());
        leavePlanningRepository.save(leavePlan);
        if (!(leavePlanningDto.getStatus() == PlanStatus.ACCEPTED))
            return new Message("You have denied this leave plan successfully");
        return new Message("You have permitted this leave plan successfully");
    }

    @Override
    public Message deleteLeavePlan(int planId) {
        LeavePlanning leavePlanning = leavePlanningRepository.findById(planId).get();
        leavePlanning.setDeleted(true);
        return new Message("Leave plan deleted successfully");
    }

    @Override
    public Message getTotalLeaveDaysOfAUser(final int userId, final int year) {
        final LocalDate startYear = LocalDate.of(year, Month.JANUARY, 1);
        final LocalDate endYear = LocalDate.of(year, Month.DECEMBER, 31);

        List<LeavePlanning> leavePlanningList = leavePlanningRepository.findAllByUser(userId);
        int totalLeaveDays = 0;

        for (LeavePlanning plan : leavePlanningList) {
            //if either fromdate or todate of plan is in the year given then it will be included
            if (plan.getStatus() == PlanStatus.ACCEPTED && (plan.getFromDate().getYear() == year) || plan.getToDate().getYear() == year) {
                int diff;
                if (plan.getToDate().getYear() != year) {
                    diff = (int)ChronoUnit.DAYS.between(plan.getFromDate(), endYear) + 1;
                } else if (plan.getFromDate().getYear() != year) {
                    diff = (int)ChronoUnit.DAYS.between(startYear, plan.getToDate()) + 1;
                } else {
                    diff = (int)ChronoUnit.DAYS.between(plan.getFromDate(), plan.getToDate()) + 1;
                }
                totalLeaveDays += diff;
            }
        }
        User user = userRepository.findById(userId).get();
        return new Message("Total days of verified leave of the year " + year + " of user '" + user.getUserName() +
                "' are: " + totalLeaveDays + "days");
    }
}
