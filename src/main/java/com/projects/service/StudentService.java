package com.projects.service;

import com.projects.beans.Student;
import com.projects.beans.StudentGroup;
import com.projects.beans.Supervisor;
import com.projects.repository.StudentGroupRepository;
import com.projects.repository.StudentsRepository;
import com.projects.repository.SupervisorRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class StudentService {
    @Autowired
    private StudentsRepository studentRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    public void importStudentsFromExcel(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Student student = new Student();
                student.setName(row.getCell(0).getStringCellValue());
                student.setRegistration_no(row.getCell(1).getStringCellValue());
                student.setDepartment(row.getCell(2).getStringCellValue());
                student.setSemester((int) row.getCell(3).getNumericCellValue());
                student.setSections(row.getCell(4).getStringCellValue());
                student.setCgpa(row.getCell(5).getNumericCellValue());
                studentRepository.save(student);
            }
        }
    }

    public void importSupervisorsFromExcel(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Supervisor supervisor = new Supervisor();
                supervisor.setName(row.getCell(0).getStringCellValue());
                supervisor.setRegistration_no(row.getCell(1).getStringCellValue());
                supervisor.setDepartment(row.getCell(2).getStringCellValue());
                supervisor.setSpecialization(row.getCell(3).getStringCellValue());
                supervisorRepository.save(supervisor);
            }
        }
    }

    public List<Student> fetchStudentsByDeptSem(String department, int semester, String section){
        return studentRepository.findByDepartmentAndSemesterAndSections(department, semester, section);
    }

    public List<Supervisor> fetchSupervisorsByDept(String department){
        return supervisorRepository.findByDepartment(department);
    }

    public List<String> fetchSpecializations(String department){
        return supervisorRepository.findDistinctSpecializationsByDepartment(department);
    }

    public List<StudentGroup> divideStudentsIntoBatches(String department, int semester,String section) {
        List<Student> students = studentRepository.findByDepartmentAndSemesterAndSections(department, semester, section);
        students.sort(Comparator.comparingDouble(Student::getCgpa).reversed());
        int batchSize = 4;
        int numberOfBatches = (int) Math.ceil((double) students.size() / batchSize);
        List<StudentGroup> studentGroups = new ArrayList<>();

        for (int i = 0; i < numberOfBatches; i++) {
            StudentGroup studentGroup = new StudentGroup();
            String batchName = generateBatchName(department, semester, section, i);
            Optional<StudentGroup> existingGroupOpt = studentGroupRepository.findByBatchName(batchName);
            if (existingGroupOpt.isPresent()) {
                studentGroup = existingGroupOpt.get();
                studentGroup.getStudents().clear();
            } else {
                studentGroup = new StudentGroup();
                studentGroup.setBatchName(batchName);
                studentGroup.setDepartment(department);
                studentGroup.setSemester(semester);
                studentGroup.setSections(section);
                studentGroup.setPassword("MVGR@33");
                studentGroup.setStudents(new ArrayList<>());
            }

        }

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            StudentGroup studentGroup = studentGroups.get(i % numberOfBatches);
            student.setStudentGroup(studentGroup);
            studentGroup.getStudents().add(student);
        }

        return studentGroupRepository.saveAll(studentGroups);
    }


    private String generateBatchName(String department, int semester, String section, int index) {
        return department + "-"+ "TEAM" + (index + 1) + section + "-" +"SEM" + semester;
    }
    /*private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }*/
}
