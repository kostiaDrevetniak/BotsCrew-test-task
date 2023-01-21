package com.botsCrew.testTask.service;

import com.botsCrew.testTask.repositoty.LectorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    public ResponseEntity<?> globalSearch(String template) {
        List<String> lectors = lectorRepository.findLectorNameByNameContaining(template.trim());
        if (lectors.isEmpty())
            return ResponseEntity.ok("Not found lectors for this template");
        return ResponseEntity.ok(lectors);
    }
}
