package com.botsCrew.testTask.entity;

import com.botsCrew.testTask.enums.Degree;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Degree degree;
    @Column(nullable = false)
    private double salary;
    @ManyToMany(mappedBy = "lectors")
    private Set<Department> departments;

    public Lector(long id, String name, Degree degree, double salary) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.salary = salary;
    }

    public Lector(String name, Degree degree, double salary) {
        this.name = name;
        this.degree = degree;
        this.salary = salary;
    }
}
