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

    @PostMapping(path="/saveStudent")
    public void saveStudent(@RequestBody List<StudentRequest> studentRequestList) {
        studentService.saveStudent(studentRequestList);
    }

    @GetMapping(path="/getStudentList")
    public List<StudentResponse> getStudentList() {
        return studentService.getStudentList();
    }

    @GetMapping(path="/getStudentById/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping(path="/getStudentByNameAndSurname")
    public StudentResponse getStudentByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return studentService.getStudentByNameAndSurname(name, surname);
    }

    @PutMapping(path="updateStudentById/{id}")
    public void updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        studentService.updateStudentById(id, studentRequest);
    }

    @PutMapping(path="/updateStudentByNameAndSurname")
    public void updateStudentByNameAndSurname(@RequestParam String name, @RequestParam String surname,@RequestBody StudentRequest studentRequest ) {
        studentService.updateStudentByNameAndSurname(name, surname, studentRequest);
    }

    @DeleteMapping(path="/deleteStudentById/{id}")
    public String deleteStudentById(@PathVariable Long id) {
        if(studentService.deleteStudentById(id)) {
            return "Student deleted successfully";
        }
        return "Student deletion failed";
    }

    @DeleteMapping(path="/deleteStudentByNameAndSurname")
    public String deleteStudentByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        if(studentService.deleteStudentByNameAndSurname(name, surname)) {
            return "Student deleted successfully";
        }
        return "Student deletion failed";
    }

}
