package com.semih.controller;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.response.RestResponse;
import com.semih.dto.response.StudentNameResponse;
import com.semih.dto.response.StudentResponse;
import com.semih.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_SAVE')")
    @PostMapping(path = "/saveStudent")
    public ResponseEntity<RestResponse<StudentResponse>> saveStudent(@RequestBody StudentRequest studentRequest) {
        StudentResponse savedStudentResponse = studentService.saveStudent(studentRequest);
        return new ResponseEntity<>(RestResponse.of(savedStudentResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_LIST')")
    @GetMapping(path = "/getStudentList")
    public ResponseEntity<RestResponse<List<StudentResponse>>> getStudentList() {
        List<StudentResponse> studentResponseList = studentService.getStudentList();
        return new ResponseEntity<>(RestResponse.of(studentResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_LIST') or hasAuthority('SCHOLARSHIP_LIST')")
    @GetMapping(path = "/getStudentNameAndSurnameList")
    public List<StudentNameResponse> getStudentNameAndSurnameList() {
        return studentService.getStudentNameAndSurnameList();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_LIST')")
    @GetMapping(path = "/getStudentById/{id}")
    public ResponseEntity<RestResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        StudentResponse getStudentResponse = studentService.getStudentById(id);
        return new ResponseEntity<>(RestResponse.of(getStudentResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_UPDATE')")
    @PutMapping(path = "updateStudentById/{id}")
    public ResponseEntity<RestResponse<StudentResponse>> updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        StudentResponse updatedStudentResponse = studentService.updateStudentById(id, studentRequest);
        return new ResponseEntity<>(RestResponse.of(updatedStudentResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_DELETE')")
    @DeleteMapping(path = "/deleteStudentById/{id}")
    public ResponseEntity<RestResponse<StudentResponse>> deleteStudentById(@PathVariable Long id) {
        StudentResponse deletedStudentResponse = studentService.deleteStudentById(id);
        return new ResponseEntity<>(RestResponse.of(deletedStudentResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('STUDENT_DELETE')")
    @DeleteMapping(path = "/deleteAllStudents")
    public ResponseEntity<RestResponse<List<StudentResponse>>> deleteAllStudents() {
        List<StudentResponse> deleteStudentResponseList = studentService.deleteAllStudents();
        return new ResponseEntity<>(RestResponse.of(deleteStudentResponseList), HttpStatus.OK);
    }

}
