package com.botsCrew.testTask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinTable(name = "head_id")
    private Lector headOfDepartment;
    @ManyToMany
    @JoinTable(name = "lector_id")
    private Set<Lector> lectors;

    public Department(long id, Lector headOfDepartment) {
        this.id = id;
        this.headOfDepartment = headOfDepartment;
    }
}
