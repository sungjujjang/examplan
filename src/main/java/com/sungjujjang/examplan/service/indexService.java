package com.sungjujjang.examplan.service;

import com.sungjujjang.examplan.util.SHA256Util;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;

import com.sungjujjang.examplan.entity.CalEntity;
import com.sungjujjang.examplan.repository.CalRepo;

@Service
public class indexService {
    private final Random random = new Random();
    private final CalRepo calRepo;

    public indexService(CalRepo calRepo) {
        this.calRepo = calRepo;
    }

    public int getRandomNumber() {
        return random.nextInt(100); // 0~99 랜덤 숫자
    }

    public CalEntity saveCal(String name, String password) {
        String pass_new = SHA256Util.hash(password);
        CalEntity cal = new CalEntity(name, pass_new);
        return calRepo.save(cal);
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
}