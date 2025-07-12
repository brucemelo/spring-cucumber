package com.brucemelo.springcucumber.service;

import com.brucemelo.springcucumber.domain.Course;
import com.brucemelo.springcucumber.domain.Student;
import com.brucemelo.springcucumber.domain.StudentCourse;
import com.brucemelo.springcucumber.domain.StudentCourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EnrollStudentService {

    private final StudentCourseRepository studentCourseRepository;

    public EnrollStudentService(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }

    @Transactional
    public void enrollStudentInCourse(Student student, Course course) {
        var studentCourse = new StudentCourse(student, course);
        studentCourseRepository.save(studentCourse);
    }

}
