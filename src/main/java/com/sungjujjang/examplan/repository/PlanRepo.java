package com.sungjujjang.examplan.repository;// Repository

import org.springframework.data.jpa.repository.JpaRepository;
import com.sungjujjang.examplan.entity.PlanEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PlanRepo extends JpaRepository<PlanEntity, Long> {
    List<PlanEntity> findByPlanyearAndPlanmonthAndPlandayAndCalid(int planyear, int planmonth, int planday, long calid);
}
