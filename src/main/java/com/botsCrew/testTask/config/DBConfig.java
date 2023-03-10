package com.botsCrew.testTask.config;

import com.botsCrew.testTask.entity.Department;
import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import com.botsCrew.testTask.repositoty.DepartmentRepository;
import com.botsCrew.testTask.repositoty.LectorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.List;

@Configuration
@Profile({"production"})
public class DBConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            LectorRepository lectorRepository,
            DepartmentRepository departmentRepository
    ) {
        return args -> {
            List<Lector> lectors = List.of(
                    new Lector("Lector 1", Degree.ASSISTANT, 5000.0),
                    new Lector("Lector 2", Degree.PROFESSOR, 7000.0),
                    new Lector("Lector 3", Degree.PROFESSOR, 10000.0)
            );
            Department department = new Department("Department 1", lectors.get(2));
            lectors = lectorRepository.saveAll(lectors);
            department.setLectors(new HashSet<>(lectors));
            departmentRepository.save(department);
        };
    }
}
