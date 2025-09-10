package com.sungjujjang.examplan.entity;

import com.sungjujjang.examplan.util.RandomStringUtil;
import jakarta.persistence.*;
import java.util.Random;

@Entity
public class CalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    @Column(unique = true, nullable = false)
    private String urlName;

    public CalEntity() {
        this.urlName = RandomStringUtil.generate(9); // 생성 시 랜덤 9글자
    }

    public CalEntity(String name, String password) {
        this.name = name;
        this.password = password;
        this.urlName = RandomStringUtil.generate(9); // 생성 시 랜덤 9글자
    }

    // Getter & Setter
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUrlName() { return urlName; }
    public void setUrlName(String urlName) { this.urlName = urlName; }
}
