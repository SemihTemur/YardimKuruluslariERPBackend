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
    public void saveStudent(List<StudentRequest> studentRequestList){
        for(StudentRequest studentRequest:studentRequestList){
           studentRepository.save(modelMapper.map(studentRequest, Student.class));
        }
    }

    // Get
    public List<StudentResponse> getStudentList(){
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for(Student student:studentList){
            studentResponseList.add(modelMapper.map(student, StudentResponse.class));
        }
        return studentResponseList;
    }

    public StudentResponse getStudentById(Long id){
        Optional<Student> student  = studentRepository.findById(id);
        if(student.isPresent()){
            return modelMapper.map(student.get(), StudentResponse.class);
        }
        return null;
    }

    public StudentResponse getStudentByNameAndSurname(String name, String surname){
        Optional<Student> student  = studentRepository.findByNameAndSurname(name, surname);
        if(student.isPresent()){
            return modelMapper.map(student.get(), StudentResponse.class);
        }
        return null;
    }

    //update
    public void updateStudentById(Long id, StudentRequest studentRequest){
        Optional<Student> student  = studentRepository.findById(id);
        if(student.isPresent()){
            modelMapper.map(studentRequest, student.get());
            studentRepository.save(student.get());
        }
        System.out.println("Bulunamadi");
    }

    public void updateStudentByNameAndSurname(String name, String surname, StudentRequest studentRequest){
        Optional<Student> student  = studentRepository.findByNameAndSurname(name, surname);
        if(student.isPresent()){
            modelMapper.map(studentRequest, student.get());
            studentRepository.save(student.get());
        }
        System.out.println("Bulunamadi");
    }


    // Delete
    @Transactional
    public boolean deleteStudentById(Long id){
        return studentRepository.deleteByIdAndReturn(id)>0;
    }

    @Transactional
    public boolean deleteStudentByNameAndSurname(String name, String surname){
        return studentRepository.deleteByNameAndSurname(name,surname)>0;
    }

}
