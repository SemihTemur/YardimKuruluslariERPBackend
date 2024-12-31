package com.semih.controller;

import com.semih.dto.request.StudentRequest;
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
    public String saveStudent(@RequestBody StudentRequest studentRequest) {
        if (studentService.saveStudent(studentRequest)) {
            return "success";
        }
        return "fail";
    }

    @GetMapping(path = "/getStudentList")
    public List<StudentResponse> getStudentList() {
        return studentService.getStudentList();
    }

    @GetMapping(path = "/getStudentById/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping(path = "updateStudentById/{id}")
    public String updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        if (studentService.updateStudentById(id, studentRequest))
            return "success";
        return "fail";
    }

    @DeleteMapping(path = "/deleteStudentById/{id}")
    public String deleteStudentById(@PathVariable Long id) {
        if (studentService.deleteStudentById(id)) {
            return "Student deleted failed";
        }
        return "Student deletion successfully";
    }

    @DeleteMapping(path = "/deleteAllStudents")
    public String deleteAllStudents() {
        studentService.deleteAllStudents();
        return "All students deleted successfully";
    }

}
