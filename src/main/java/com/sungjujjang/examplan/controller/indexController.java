package com.sungjujjang.examplan.controller;

import com.sungjujjang.examplan.service.indexService;
import com.sungjujjang.examplan.util.SHA256Util;
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
}
