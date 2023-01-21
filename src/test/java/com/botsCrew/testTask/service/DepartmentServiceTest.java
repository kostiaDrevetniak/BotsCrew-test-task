package com.botsCrew.testTask.repository.service;

import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import com.botsCrew.testTask.repositoty.DepartmentRepository;
import com.botsCrew.testTask.service.DepartmentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testGetHeadOfDepartmentForNotExitedName() {
        Mockito.when(departmentRepository.findHeadOfDepartmentByName(NOT_EXISTED_DEPARTMENT_NAME))
                .thenReturn(Optional.empty());
        ResponseEntity<?> response = departmentService.getHeadOfDepartment(NOT_EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);
        assertThat(response.getBody()).isEqualTo(NOT_EXISTED_DEPARTMENT_NAME_ERROR_TEXT);
    }

    @Test
    public void testGetHeadOfDepartmentForExitedName() {
        Mockito.when(departmentRepository.findHeadOfDepartmentByName(EXISTED_DEPARTMENT_NAME))
                .thenReturn(Optional.of(HEAD_OF_DEPARTMENT));
        ResponseEntity<?> response = departmentService.getHeadOfDepartment(EXISTED_DEPARTMENT_NAME);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(HEAD_OF_DEPARTMENT_NAME);
    }
}
