package com.botsCrew.testTask.controller;

import com.botsCrew.testTask.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/head")
    public ResponseEntity<?> getHeadOfDepartment(@RequestParam String departmentName) {
        return departmentService.getHeadOfDepartment(departmentName);
    }
}
