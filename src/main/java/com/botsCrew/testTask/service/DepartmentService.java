package com.botsCrew.testTask.service;

import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import com.botsCrew.testTask.exception.EntityNotFoundException;
import com.botsCrew.testTask.repositoty.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public ResponseEntity<?> getDepartmentStatistic(String departmentName) {
        Map<Degree, Integer> response = new LinkedHashMap<>();
        try {
            for (Degree degree : Degree.values()) {
                Integer count = departmentRepository.countLectorsByNameAndLectorsDegree(departmentName, degree)
                        .orElseThrow(() -> new EntityNotFoundException("Don`t found department with this name"));
                response.put(degree, count);
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getAverageSalary(String departmentName) {
        Double salary;
        try {
            salary = departmentRepository.averageSalaryByName(departmentName)
                    .orElseThrow(() -> new EntityNotFoundException("Don`t found department with this name"));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok(salary);
    }
}
