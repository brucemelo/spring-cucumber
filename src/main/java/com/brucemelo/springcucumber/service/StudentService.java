package com.brucemelo.springcucumber.service;

import com.brucemelo.springcucumber.domain.Course;
import com.brucemelo.springcucumber.domain.Student;
import com.brucemelo.springcucumber.domain.StudentCourse;
import com.brucemelo.springcucumber.domain.StudentCourseRepository;
import com.brucemelo.springcucumber.domain.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public StudentService(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Set<Course> getEnrolledCourses(Student student) {
        var studentCourses = studentCourseRepository.findByStudent(student);
        return studentCourses.stream()
                .map(StudentCourse::getCourse)
                .collect(toSet());
    }

}