package com.sungjujjang.examplan.entity;

import com.sungjujjang.examplan.util.RandomStringUtil;
import jakarta.persistence.*;
import java.util.Random;

@Entity
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int planyear;
    private int planmonth;
    private int planday;

    private String name;
    private Long calid;

    protected PlanEntity() { }

    public PlanEntity(int planyear, int planmonth, int planday, String name, Long calid) {
        this.planyear = planyear;
        this.planmonth = planmonth;
        this.planday = planday;
        this.name = name;
        this.calid = calid;
    } //h2

    public Long getId() { return id; }

    public int getYear() { return planyear; }
    public void setYear(int planyear) { this.planyear = planyear; }

    public int getMonth() { return planmonth; }
    public void setMonth(int planmonth) { this.planmonth = planmonth; }

    public int getDay() { return planday; }
    public void setDay(int planday) { this.planday = planday; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getCalId() { return calid; }
}
