package com.sungjujjang.examplan.controller;

import com.sungjujjang.examplan.entity.PlanEntity;
import com.sungjujjang.examplan.service.indexService;
import com.sungjujjang.examplan.util.SHA256Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import com.sungjujjang.examplan.entity.CalEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/index")
public class indexController {
    private final indexService service;

    public indexController(indexService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public String saveCal(
            @RequestParam String name,
            @RequestParam String password
    ) {
        CalEntity saved = service.saveCal(name, password);
        String u = "http://localhost:8080/index/" + saved.getUrlName() + "?password=" + password;
        return "<script>location.href="+u+"</script>";
    }

    @GetMapping("/{urlparam}")
    public String enterCal(
            @PathVariable String urlparam,
            @RequestParam String password
    ) {
        Optional<CalEntity> cal = service.getCalByUrl(urlparam, password);
        if (cal.isPresent()) {
            CalEntity c = cal.get();
            String u = "http://localhost:8080/index/"+c.getUrlName()+"?password="+password;
            return "ID : " + c.getId() +
                    "\nPW : " + c.getPassword() +
                    "\nNAME : " + c.getName() +
                    "\nURL : <a href='"+u+"'>"+u+"</a>";
        } else {
            return "조회된 캘린더가 없습니다.";
        }
    }

    @GetMapping("/{urlparam}/plan")
    public String getplans(
            @PathVariable String urlparam,
            @RequestParam String password,
            @RequestParam Integer planyear,
            @RequestParam Integer planmonth,
            @RequestParam Integer planday
    ) throws JSONException {
        Optional<CalEntity> cal = service.getCalByUrl(urlparam, password);
        if (cal.isPresent()) {
            CalEntity c = cal.get();
            Long calid = c.getId();
            List<PlanEntity> plans = service.getPlansByDate(planyear, planmonth, planday, calid);
            if (!plans.isEmpty()) {
                JSONArray plan_s = new JSONArray();
                for (PlanEntity plan : plans) {
                    JSONObject tempplan = new JSONObject();
                    tempplan.put("id", plan.getId());
                    tempplan.put("name", plan.getName());
                    tempplan.put("planyear", plan.getYear());
                    tempplan.put("planmonth", plan.getMonth());
                    tempplan.put("planday", plan.getDay());
                    plan_s.put(tempplan);
                }
                return plan_s.toString();
            } else {
                return "no";
            }
        } else {
            return "no";
        }
    }

    @PostMapping("/{urlparam}/plan/add")
    public String addPlan(
            @PathVariable String urlparam,
            @RequestParam String password,
            @RequestParam Integer planyear,
            @RequestParam Integer planmonth,
            @RequestParam Integer planday,
            @RequestParam String name
    ) {
        Optional<CalEntity> cal = service.getCalByUrl(urlparam, password);
        if (cal.isPresent()) {
            CalEntity c = cal.get();
            Long calid = c.getId();
            PlanEntity plan = service.addPlan(planyear, planmonth, planday, name, calid);
            return "Plan saved with id: " + plan.getId();
        } else {
            return "Invalid url or password";
        }
    }

    @DeleteMapping("/{urlparam}/plan/delete/{planId}")
    public String deletePlan(
            @PathVariable String urlparam,
            @PathVariable Long planId,
            @RequestParam String password
    ) {
        Optional<CalEntity> cal = service.getCalByUrl(urlparam, password);
        if (cal.isPresent()) {
            boolean deleted = service.deletePlan(planId);
            if (deleted) {
                return "Plan deleted successfully";
            } else {
                return "Plan not found";
            }
        } else {
            return "Invalid url or password";
        }
    }
}
