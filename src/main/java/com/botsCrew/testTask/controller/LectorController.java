package com.botsCrew.testTask.controller;

import com.botsCrew.testTask.service.LectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lector")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> globalSearch(@RequestParam String template) {
        return lectorService.globalSearch(template);
    }
}
