package com.botsCrew.testTask.repository;

import com.botsCrew.testTask.entity.Lector;
import com.botsCrew.testTask.enums.Degree;
import com.botsCrew.testTask.repositoty.LectorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LectorRepositoryTest {
    private static final List<Lector> LECTORS = List.of(
            new Lector("Ivan Petrenko", Degree.ASSISTANT, 2000.0),
            new Lector("Petro Koval", Degree.ASSOCIATE_PROFESSOR, 2000.0),
            new Lector("Andriy Zaremba", Degree.PROFESSOR, 4000.0)
    );

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LectorRepository lectorRepository;


    @BeforeEach
    public void setUp() {
        for (Lector lector : LECTORS)
            entityManager.persist(lector);
        entityManager.flush();
    }

    @AfterEach
    public void clear() {
        LECTORS.forEach(lector -> lector.setId(0));
        entityManager.clear();
    }

    @Test
    public void testFoundByTemplate() {
        List<String> lectors = lectorRepository.findLectorNameByNameContaining("Petr");
        assertThat(lectors.isEmpty()).isFalse();
        assertThat(lectors.size()).isEqualTo(2);
        assertThat(lectors.containsAll(List.of("Ivan Petrenko", "Petro Koval"))).isTrue();
    }
}
