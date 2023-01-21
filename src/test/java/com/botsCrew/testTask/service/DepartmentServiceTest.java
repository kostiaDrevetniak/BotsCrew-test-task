package com.botsCrew.testTask.service;

import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import com.botsCrew.testTask.repositoty.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentServiceTest {
    private static final String HEAD_OF_DEPARTMENT_NAME = "Head of department";
    private static final String EXISTED_DEPARTMENT_NAME = "Existed department";
    private static final String NOT_EXISTED_DEPARTMENT_NAME = "Not existed department";

    private static final Lector HEAD_OF_DEPARTMENT =
            new Lector(1, HEAD_OF_DEPARTMENT_NAME, Degree.PROFESSOR, 1000.0);

    private static final String NOT_EXISTED_DEPARTMENT_NAME_ERROR_TEXT = "Don`t found department with this name";
    @Autowired
    private DepartmentService departmentService;
    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    public void testGetHeadOfDepartmentForNotExitedDepartment() {
        Mockito.when(departmentRepository.findHeadOfDepartmentByName(NOT_EXISTED_DEPARTMENT_NAME))
                .thenReturn(Optional.empty());
        ResponseEntity<?> response = departmentService.getHeadOfDepartment(NOT_EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);
        assertThat(response.getBody()).isEqualTo(NOT_EXISTED_DEPARTMENT_NAME_ERROR_TEXT);
    }

    @Test
    public void testGetHeadOfDepartmentForExitedDepartment() {
        Mockito.when(departmentRepository.findHeadOfDepartmentByName(EXISTED_DEPARTMENT_NAME))
                .thenReturn(Optional.of(HEAD_OF_DEPARTMENT));
        ResponseEntity<?> response = departmentService.getHeadOfDepartment(EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(HEAD_OF_DEPARTMENT_NAME);
    }

    @Test
    public void testGetDepartmentStatisticForNotExistedDepartment() {
        Mockito.when(departmentRepository.existsByName(NOT_EXISTED_DEPARTMENT_NAME))
                .thenReturn(false);
        ResponseEntity<?> response = departmentService.getDepartmentStatistic(NOT_EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);
        assertThat(response.getBody()).isEqualTo(NOT_EXISTED_DEPARTMENT_NAME_ERROR_TEXT);
    }

    @Test
    public void testGetDepartmentStatisticForExistedDepartment() {
        Mockito.when(departmentRepository.countLectorsByNameAndLectorsDegree(eq(EXISTED_DEPARTMENT_NAME), any(Degree.class)))
                .thenReturn(2);
        Mockito.when(departmentRepository.existsByName(EXISTED_DEPARTMENT_NAME)).thenReturn(true);
        ResponseEntity<?> response = departmentService.getDepartmentStatistic(EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Map<Degree, Integer> body = (Map) response.getBody();
        for (Degree degree : Degree.values()) {
            assertThat(body.containsKey(degree)).isTrue();
            assertThat(body.get(degree)).isEqualTo(2);
        }
    }

    @Test
    public void testGetAverageSalaryForNotExitedDepartment() {
        Mockito.when(departmentRepository.averageSalaryByName(NOT_EXISTED_DEPARTMENT_NAME))
                .thenReturn(Optional.empty());
        ResponseEntity<?> response = departmentService.getAverageSalary(NOT_EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);
        assertThat(response.getBody()).isEqualTo(NOT_EXISTED_DEPARTMENT_NAME_ERROR_TEXT);
    }

    @Test
    public void testGetAverageSalaryForExitedDepartment() {
        Mockito.when(departmentRepository.averageSalaryByName(EXISTED_DEPARTMENT_NAME))
                .thenReturn(Optional.of(5000.0));
        ResponseEntity<?> response = departmentService.getAverageSalary(EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(5000.0);
    }

    @Test
    public void testGetEmployeeForNotExistedDepartment() {
        Mockito.when(departmentRepository.existsByName(NOT_EXISTED_DEPARTMENT_NAME))
                .thenReturn(false);
        ResponseEntity<?> response = departmentService.getEmployeeCount(NOT_EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);
        assertThat(response.getBody()).isEqualTo(NOT_EXISTED_DEPARTMENT_NAME_ERROR_TEXT);
    }

    @Test
    public void testGetEmployeeCountForExistedDepartment() {
        Mockito.when(departmentRepository.countLectorsByName(EXISTED_DEPARTMENT_NAME))
                .thenReturn(5);
        Mockito.when(departmentRepository.existsByName(EXISTED_DEPARTMENT_NAME)).thenReturn(true);
        ResponseEntity<?> response = departmentService.getEmployeeCount(EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(5);
    }
}
