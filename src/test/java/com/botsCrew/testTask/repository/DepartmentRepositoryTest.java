package com.botsCrew.testTask.repository;

import com.botsCrew.testTask.entity.Department;
import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import com.botsCrew.testTask.repositoty.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DepartmentRepositoryTest {
    private static final List<Lector> LECTORS = List.of(
            new Lector("Test lector 1", Degree.ASSISTANT, 2000.0),
            new Lector("Test lector 2", Degree.ASSISTANT, 2000.0),
            new Lector("Test lector 3", Degree.ASSOCIATE_PROFESSOR, 4000.0),
            new Lector("Test lector 4", Degree.ASSOCIATE_PROFESSOR, 4000.0),
            new Lector("Test lector 5", Degree.PROFESSOR, 8000.0),
            new Lector("Test lector 6", Degree.PROFESSOR, 8000.0)
    );
    private static final List<Department> DEPARTMENTS = List.of(
            new Department("Test department 1", LECTORS.get(4)),
            new Department("Test department 2", LECTORS.get(3)),
            new Department("Test department 3", LECTORS.get(5))
    );

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        for (Lector lector : LECTORS)
            entityManager.persist(lector);
        entityManager.flush();
        DEPARTMENTS.get(0).getLectors().addAll(List.of(LECTORS.get(4), LECTORS.get(0), LECTORS.get(2)));
        DEPARTMENTS.get(1).getLectors().addAll(List.of(LECTORS.get(3), LECTORS.get(1), LECTORS.get(5)));
        DEPARTMENTS.get(2).getLectors().addAll(List.of(LECTORS.get(5), LECTORS.get(0), LECTORS.get(3)));
        for (Department department : DEPARTMENTS) {
            entityManager.persist(department);
        }
        entityManager.flush();
    }

    @AfterEach
    public void clear() {
        LECTORS.forEach(lector -> lector.setId(0));
        DEPARTMENTS.forEach(department -> {
            department.setId(0);
            department.setLectors(new HashSet<>());
        });
        entityManager.clear();
    }

    @Test
    public void testGetHeadOfDepartment() {
        Lector headOfDepartment = departmentRepository.findHeadOfDepartmentByName("Test department 1").get();
        assertThat(headOfDepartment).isEqualTo(LECTORS.get(4));
    }

    @Test
    public void testCountLectors() {
        for (Degree degree : Degree.values())
            assertThat(departmentRepository
                    .countLectorsByNameAndLectorsDegree("Test department 2", degree)).isEqualTo(1);
    }

    @Test
    public void testAverageSalary() {
        double  salary = departmentRepository.averageSalaryByName("Test department 3").get();
        double trueSalary = DEPARTMENTS.get(2).getLectors().stream().map(Lector::getSalary).reduce(Double::sum).get() / 3;
        assertThat(salary).isEqualTo(trueSalary);
    }

    @Test
    public void testExistByName() {
        assertThat(departmentRepository.existsByName("Test department 1")).isTrue();
        assertThat(departmentRepository.existsByName("Not existed")).isFalse();
    }

    @Test
    public void testLectorsCount() {
        assertThat(departmentRepository.countLectorsByName("Test department 2")).isEqualTo(3);
    }
}
