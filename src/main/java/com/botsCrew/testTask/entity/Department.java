package com.botsCrew.testTask.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "head_id")
    private Lector headOfDepartment;
    @ManyToMany
    @JoinTable(name = "department_lectors")
    private Set<Lector> lectors;

    public Department(long id, String name, Lector headOfDepartment) {
        this.id = id;
        this.name = name;
        this.headOfDepartment = headOfDepartment;
        this.lectors = new HashSet<>();
    }

    public Department(String name, Lector headOfDepartment) {
        this.name = name;
        this.headOfDepartment = headOfDepartment;
        this.lectors = new HashSet<>();
    }
}
