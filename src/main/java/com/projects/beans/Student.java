package com.projects.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "DS_STUDENT_P")
public class Student {
    private String name;
    @Id
    private String registration_no;
    private String department;
    private int semester;
    private String sections;
    private double cgpa;
}
