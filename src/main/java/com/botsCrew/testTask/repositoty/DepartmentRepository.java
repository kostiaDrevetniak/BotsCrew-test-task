package com.botsCrew.testTask.repositoty;

import com.botsCrew.testTask.entity.Department;
import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT department.headOfDepartment FROM Department department WHERE department.name = ?1")
    Optional<Lector> findHeadOfDepartmentByName(String name);

    Optional<Integer> countLectorsByNameAndLectorsDegree(String name, Degree degree);

    @Query("SELECT AVG(lector.salary) FROM " +
            "(SELECT lectors as lector FROM Department department WHERE department.name = ?1)")
    Optional<Double> averageSalaryByName(String name);
}
