package com.semih.service;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.StudentNameResponse;
import com.semih.dto.response.StudentResponse;
import com.semih.exception.ConflictException;
import com.semih.exception.NotFoundException;
import com.semih.model.Address;
import com.semih.model.Auditable;
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

    private void validateUniqueness(StudentRequest studentRequest) {
        if (studentRepository.existsByNameAndSurname(studentRequest.name(), studentRequest.surname())) {
            throw new ConflictException("Bu öğrenci adı ve soyadı mevcut!!!");
        }

        if (studentRepository.existsByTckn(studentRequest.tckn())) {
            throw new ConflictException("Bu TC mevcut!!!");
        }

        if (studentRepository.existsByPhoneNumber(studentRequest.phoneNumber())) {
            throw new ConflictException("Bu telefon numarası mevcut!!!");
        }

        if (studentRepository.existsByEmail(studentRequest.email())) {
            throw new ConflictException("Bu email mevcut!!!");
        }
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Student")
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

    public Long getStudentCount() {
        return studentRepository.count();
    }

    public Student getStudentByNameAndSurname(String name, String surname) {
        return studentRepository.findByNameAndSurname(name, surname)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + name + surname));
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Student")
    public StudentResponse updateStudentById(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + id));
//        validateUniqueness(studentRequest);
        Student updatedStudent = mapToEntity(studentRequest);
        updatedStudent.setId(id);
        updatedStudent.setCreatedDate(student.getCreatedDate());
        return mapToResponse(studentRepository.save(updatedStudent));
    }

    @Auditable(actionType = "Sildi", targetEntity = "Student")
    @Transactional
    public StudentResponse deleteStudentById(Long id) {
        Student deletedStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Öğrenci bulunamadı!!!" + id));
        studentRepository.delete(deletedStudent);
        return mapToResponse(deletedStudent);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Student")
    public List<StudentResponse> deleteAllStudents() {
        List<StudentResponse> deletedStudentResponseList = getStudentList();
        studentRepository.deleteAll();
        return deletedStudentResponseList;
    }

}
