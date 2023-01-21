package com.botsCrew.testTask.repositoty;

import com.botsCrew.testTask.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LectorRepository extends JpaRepository<Lector, Long> {
    @Query("SELECT lector.name FROM Lector lector WHERE lector.name LIKE %?1%")
    List<String> findLectorNameByNameContaining(String template);
}
