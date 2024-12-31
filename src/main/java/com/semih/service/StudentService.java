package com.semih.service;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.response.StudentResponse;
import com.semih.model.Student;
import com.semih.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    // Post
    public boolean saveStudent(StudentRequest studentRequest) {
        Student savedStudent = studentRepository.save(modelMapper.map(studentRequest, Student.class));
        return savedStudent != null;
    }

    // Get
    public List<StudentResponse> getStudentList() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (Student student : studentList) {
            studentResponseList.add(modelMapper.map(student, StudentResponse.class));
        }
        return studentResponseList;
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(null);
        return modelMapper.map(student, StudentResponse.class);
    }

    public Student getStudentByNameAndSurname(String name, String surname) {
        return studentRepository.findByNameAndSurname(name, surname).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    //update
    public boolean updateStudentById(Long id, StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            modelMapper.map(studentRequest, student.get());
            studentRepository.save(student.get());
            return true;
        }
        return false;
    }

    // Delete
    @Transactional
    public boolean deleteStudentById(Long id) {
        studentRepository.deleteById(id);

        Optional<Student> student = studentRepository.findById(id);

        return student.isPresent();
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

}
