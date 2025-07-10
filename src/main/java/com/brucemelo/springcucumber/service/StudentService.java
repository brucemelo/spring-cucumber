package com.brucemelo.springcucumber.service;

import com.brucemelo.springcucumber.domain.Course;
import com.brucemelo.springcucumber.domain.Student;
import com.brucemelo.springcucumber.domain.StudentCourse;
import com.brucemelo.springcucumber.domain.StudentCourseRepository;
import com.brucemelo.springcucumber.domain.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public StudentService(StudentRepository studentRepository, StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Set<Course> getEnrolledCourses(Student student) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(student);
        Set<Course> courses = new HashSet<>();
        for (StudentCourse sc : studentCourses) {
            courses.add(sc.getCourse());
        }
        return courses;
    }
}