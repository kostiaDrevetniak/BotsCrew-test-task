package com.botsCrew.testTask.service;

import com.botsCrew.testTask.repositoty.LectorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class LectorServiceTest {
    private static final String EXISTED_TEMPLATE = "Existed template";
    private static final String NOT_EXISTED_TEMPLATE = "Existed template";

    private static final List<String> LECTORS_NAME = List.of("Lector 1", "Lector 2");

    @Autowired
    private LectorService lectorService;
    @MockBean
    private LectorRepository lectorRepository;

    @Test
    public void testGlobalSearchForNotExistedTemplate() {
        Mockito.when(lectorRepository.findLectorNameByNameContaining(NOT_EXISTED_TEMPLATE))
                .thenReturn(Collections.emptyList());
        ResponseEntity<?> response = lectorService.globalSearch(NOT_EXISTED_TEMPLATE);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Not found lectors for this template");
    }

    @Test
    public void testGlobalSearchForExistedTemplate() {
        Mockito.when(lectorRepository.findLectorNameByNameContaining(EXISTED_TEMPLATE))
                .thenReturn(LECTORS_NAME);
        ResponseEntity<?> response = lectorService.globalSearch(NOT_EXISTED_TEMPLATE);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(LECTORS_NAME);
    }
}
