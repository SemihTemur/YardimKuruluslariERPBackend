package com.semih.controller;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.response.StudentNameResponse;
import com.semih.dto.response.StudentResponse;
import com.semih.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/saveStudent")
    public void saveStudent(@RequestBody StudentRequest studentRequest) {
        studentService.saveStudent(studentRequest);
    }

    @GetMapping(path = "/getStudentList")
    public List<StudentResponse> getStudentList() {
        return studentService.getStudentList();
    }

    @GetMapping(path = "/getStudentNameAndSurnameList")
    public List<StudentNameResponse> getStudentNameAndSurnameList() {
        return studentService.getStudentNameAndSurnameList();
    }

    @GetMapping(path = "/getStudentById/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping(path = "updateStudentById/{id}")
    public void updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        studentService.updateStudentById(id, studentRequest);
    }

    @DeleteMapping(path = "/deleteStudentById/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    @DeleteMapping(path = "/deleteAllStudents")
    public void deleteAllStudents() {
        studentService.deleteAllStudents();
    }

}
