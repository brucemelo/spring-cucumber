package com.brucemelo.springcucumber.service;

import com.brucemelo.springcucumber.domain.Course;
import com.brucemelo.springcucumber.domain.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Optional<Course> findCourseByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

}