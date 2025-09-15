package com.sungjujjang.examplan.service;

import com.sungjujjang.examplan.entity.PlanEntity;
import com.sungjujjang.examplan.repository.PlanRepo;
import com.sungjujjang.examplan.util.SHA256Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;

import com.sungjujjang.examplan.entity.CalEntity;
import com.sungjujjang.examplan.repository.CalRepo;

@Service
public class indexService {
    private final Random random = new Random();
    private final CalRepo calRepo;
    private final PlanRepo planRepo;

    public indexService(CalRepo calRepo, PlanRepo planRepo) {
        this.calRepo = calRepo;
        this.planRepo = planRepo;
    }

    public int getRandomNumber() {
        return random.nextInt(100); // 0~99 랜덤 숫자
    }

    public CalEntity saveCal(String name, String password) {
        String pass_new = SHA256Util.hash(password);
        CalEntity cal = new CalEntity(name, pass_new);
        return calRepo.save(cal);
    }

    public boolean deleteCal(Long calId) {
        Optional<CalEntity> calOpt = calRepo.findById(calId);
        if (calOpt.isPresent()) {
            calRepo.deleteById(calId);
            return true;
        }
        return false;
    }

    public Optional<CalEntity> getCalById(Long id, String password) {
        Optional<CalEntity> calOpt = calRepo.findById(id);
        if (calOpt.isPresent()) {
            CalEntity cal = calOpt.get();  // 실제 객체 꺼내기
            String server_pass = cal.getPassword();
            if (!SHA256Util.matches(password, server_pass)) {
                calOpt = Optional.empty();
            }
        }
        return calOpt;
    }

    public Optional<CalEntity> getCalByUrl(String Url, String password) {
        Optional<CalEntity> calOpt = calRepo.findByUrlName(Url);
        if (calOpt.isPresent()) {
            CalEntity cal = calOpt.get();  // 실제 객체 꺼내기
            String server_pass = cal.getPassword();
            if (!SHA256Util.matches(password, server_pass)) {
                calOpt = Optional.empty();
            }
        }
        return calOpt;
    }

    public List<PlanEntity> getPlansByDate(int planyear, int planmonth, int planday, long calid) {
        return planRepo.findByPlanyearAndPlanmonthAndPlandayAndCalid(planyear, planmonth, planday, calid);
    }

    public PlanEntity addPlan(int planyear, int planmonth, int planday, String name, Long calid) {
        PlanEntity plan = new PlanEntity(planyear, planmonth, planday, name, calid);
        return planRepo.save(plan);
    }

    public boolean deletePlan(Long planId) {
        Optional<PlanEntity> planOpt = planRepo.findById(planId);
        if (planOpt.isPresent()) {
            planRepo.deleteById(planId);
            return true;
        }
        return false;
    }
}