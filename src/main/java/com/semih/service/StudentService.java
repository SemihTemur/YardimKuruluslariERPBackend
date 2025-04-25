package com.semih.service;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.StudentNameResponse;
import com.semih.dto.response.StudentResponse;
import com.semih.exception.NotFoundException;
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

    private Student mapToEntity(StudentRequest studentRequest) {
        return new Student(
                studentRequest.name(),
                studentRequest.surname(),
                studentRequest.age(),
                studentRequest.tckn(),
                studentRequest.phoneNumber(),
                studentRequest.email(),
                studentRequest.genderType(),
                studentRequest.educationLevel(),
                new Address(
                        studentRequest.address().city(),
                        studentRequest.address().district(),
                        studentRequest.address().neighborhood(),
                        studentRequest.address().street(),
                        studentRequest.address().userType()
                )
        );
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                new BaseResponse(student.getId(),
                        student.getCreatedDate(),
                        student.getModifiedDate()),
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
    public StudentResponse saveStudent(StudentRequest studentRequest) {
        Student savedStudent = studentRepository.save(mapToEntity(studentRequest));
        return mapToResponse(savedStudent);
    }

    // Get
    public List<StudentResponse> getStudentList() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (Student student : studentList) {
            studentResponseList.add(mapToResponse(student));
        }
        return studentResponseList;
    }

    public List<StudentNameResponse> getStudentNameAndSurnameList() {
        return studentRepository.getStudentNameAndSurnameList();
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + id));
        return mapToResponse(student);
    }

    public Student getStudentByNameAndSurname(String name, String surname) {
        return studentRepository.findByNameAndSurname(name, surname)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + name + surname));
    }

    //update
    public StudentResponse updateStudentById(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + id));
        Student updatedStudent = mapToEntity(studentRequest);
        updatedStudent.setId(id);
        updatedStudent.setCreatedDate(student.getCreatedDate());
        return mapToResponse(studentRepository.save(updatedStudent));
    }

    // Delete
    @Transactional
    public StudentResponse deleteStudentById(Long id) {
        Student deletedStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + id));
        studentRepository.delete(deletedStudent);
        return mapToResponse(deletedStudent);
    }

    public List<StudentResponse> deleteAllStudents() {
        List<StudentResponse> deletedStudentResponseList = getStudentList();
        studentRepository.deleteAll();
        return deletedStudentResponseList;
    }

}
