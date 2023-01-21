package com.botsCrew.testTask.service;

import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.exception.EntityNotFoundException;
import com.botsCrew.testTask.repositoty.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<?> getHeadOfDepartment(String departmentName) {
        Lector lector;
        try {
            lector = departmentRepository.findHeadOfDepartmentByName(departmentName)
                    .orElseThrow(() -> new EntityNotFoundException("Don`t found department with this name"));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok(lector.getName());
    }
}
