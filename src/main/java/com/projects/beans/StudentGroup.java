package com.projects.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchName;

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.ALL)
    private List<Student> students;

    private String password;

    @Column(name = "semester")
    private int semester;

    @Column(name = "department")
    private String department;
    @Column(name = "sections")
    private String sections;
}
