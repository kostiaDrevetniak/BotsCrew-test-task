package com.botsCrew.testTask.repositoty;

import com.botsCrew.testTask.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
