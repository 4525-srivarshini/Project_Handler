package com.projects.repository;

import com.projects.beans.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, String> {
    List<Supervisor> findByDepartment(String department);

    @Query("SELECT DISTINCT s.specialization FROM Supervisor s WHERE s.department = :department")
    List<String> findDistinctSpecializationsByDepartment(String department);



}
