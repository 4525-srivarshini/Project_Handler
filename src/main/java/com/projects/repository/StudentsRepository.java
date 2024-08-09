package com.projects.repository;

import com.projects.beans.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentsRepository extends JpaRepository<Student, String> {
    List<Student> findByDepartmentAndSemester(String department, int semester);
}
