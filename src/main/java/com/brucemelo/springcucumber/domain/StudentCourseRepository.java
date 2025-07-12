package com.brucemelo.springcucumber.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    @EntityGraph(attributePaths = StudentCourse_.COURSE)
    List<StudentCourse> findByStudent(Student student);
}