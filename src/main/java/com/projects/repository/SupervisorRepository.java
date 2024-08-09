package com.projects.repository;

import com.projects.beans.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupervisorRepository extends JpaRepository<Supervisor, String> {
    List<Supervisor> findByDepartment(String department);

}
