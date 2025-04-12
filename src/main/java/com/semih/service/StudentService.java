package com.semih.service;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.StudentNameResponse;
import com.semih.dto.response.StudentResponse;
import com.semih.model.Address;
import com.semih.model.Student;
import com.semih.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private Student mapDtoToEntity(StudentRequest studentRequest) {
        return new Student(
                studentRequest.getName(),
                studentRequest.getSurname(),
                studentRequest.getAge(),
                studentRequest.getTckn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail(),
                studentRequest.getGenderType(),
                studentRequest.getEducationLevel(),
                new Address(
                        studentRequest.getAddress().getCity(),
                        studentRequest.getAddress().getDistrict(),
                        studentRequest.getAddress().getNeighborhood(),
                        studentRequest.getAddress().getStreet(),
                        studentRequest.getAddress().getUserType()
                )
        );
    }

    private StudentResponse mapEntityToResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getCreatedDate(),
                student.getModifiedDate(),
                student.getName(),
                student.getSurname(),
                student.getAge(),
                student.getTckn(),
                student.getPhoneNumber(),
                student.getEmail(),
                student.getGenderType(),
                student.getEducationLevel(),
                new AddressResponse(
                        student.getAddress().getCity(),
                        student.getAddress().getDistrict(),
                        student.getAddress().getNeighborhood(),
                        student.getAddress().getStreet()
                )
        );
    }

    // Post
    public void saveStudent(StudentRequest studentRequest) {
        studentRepository.save(mapDtoToEntity(studentRequest));
    }

    // Get
    public List<StudentResponse> getStudentList() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (Student student : studentList) {
            studentResponseList.add(mapEntityToResponse(student));
        }
        return studentResponseList;
    }

    public List<StudentNameResponse> getStudentNameAndSurnameList() {
        return studentRepository.getStudentNameAndSurnameList();
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(null);
        return mapEntityToResponse(student);
    }

    public Student getStudentByNameAndSurname(String name, String surname) {
        return studentRepository.findByNameAndSurname(name, surname).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    //update
    public void updateStudentById(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        Student updatedStudent = mapDtoToEntity(studentRequest);
        updatedStudent.setId(id);
        updatedStudent.setCreatedDate(student.getCreatedDate());
        studentRepository.save(updatedStudent);
    }

    // Delete
    @Transactional
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

}
