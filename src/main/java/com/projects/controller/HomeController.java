package com.projects.controller;

import com.projects.beans.Student;
import com.projects.beans.Supervisor;
import com.projects.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class HomeController {

    private final StudentService studentService;

    @Autowired
    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/uploadStudentsData")
    public ResponseEntity<String> uploadStudentsData(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            studentService.importStudentsFromExcel(file);
            return ResponseEntity.ok("Students imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import students: " + e.getMessage());
        }

    }

    @PostMapping("/uploadSupervisorsData")
    public ResponseEntity<String> uploadSupervisorsData(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            studentService.importSupervisorsFromExcel(file);
            return ResponseEntity.ok("Supervisors imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import students: " + e.getMessage());
        }
    }

    @GetMapping("/getStudentsData")
    public ResponseEntity<?> getStudentsData(@RequestParam String department, @RequestParam int semester) {
        try {
            List<Student> students = studentService.fetchStudentsByDeptSem(department, semester);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return students: " + e.getMessage());
        }
    }

    @GetMapping("/getSupervisorsData")
    public ResponseEntity<?> getSupervisorsData(@RequestParam String department) {
        try {
            List<Supervisor> supervisors = studentService.fetchSupervisorsByDept(department);
            return ResponseEntity.ok(supervisors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return supervisors: " + e.getMessage());
        }
    }

    @GetMapping("/divideStudents")
    public ResponseEntity<List<List<Student>>> divideStudents(@RequestParam String department, @RequestParam int semester) {
        List<List<Student>> batches = studentService.divideStudentsIntoBatches(department, semester);
        return ResponseEntity.ok(batches);
    }


}
