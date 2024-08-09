package com.projects.service;

import com.projects.beans.Student;
import com.projects.beans.Supervisor;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentsRepository studentRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

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

    public List<Student> fetchStudentsByDeptSem(String department, int semester){
        return studentRepository.findByDepartmentAndSemester(department, semester);
    }

    public List<Supervisor> fetchSupervisorsByDept(String department){
        return supervisorRepository.findByDepartment(department);
    }

    public List<List<Student>> divideStudentsIntoBatches(String department, int semester) {
        List<Student> students = studentRepository.findByDepartmentAndSemester(department, semester);
        students.sort(Comparator.comparingDouble(Student::getCgpa).reversed());
        int batchSize = 4;
        int numberOfBatches = (int) Math.ceil((double) students.size() / batchSize);
        List<List<Student>> batches = new ArrayList<>();
        for (int i = 0; i < numberOfBatches; i++) {
            batches.add(new ArrayList<>());
        }
        for (int i = 0; i < students.size(); i++) {
            batches.get(i % numberOfBatches).add(students.get(i));
        }

        return batches;
    }
}
