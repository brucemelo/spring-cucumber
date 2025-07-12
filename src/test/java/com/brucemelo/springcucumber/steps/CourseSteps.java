package com.brucemelo.springcucumber.steps;

import com.brucemelo.springcucumber.domain.Course;
import com.brucemelo.springcucumber.domain.CourseRepository;
import com.brucemelo.springcucumber.domain.Student;
import com.brucemelo.springcucumber.service.CourseService;
import com.brucemelo.springcucumber.service.EnrollStudentService;
import com.brucemelo.springcucumber.service.StudentService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseSteps {

    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollStudentService enrollStudentInCourseService;

    public CourseSteps(CourseRepository courseRepository,
                       CourseService courseService,
                       StudentService studentService,
                       EnrollStudentService enrollStudentInCourseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.studentService = studentService;
        this.enrollStudentInCourseService = enrollStudentInCourseService;
    }

    private Course foundCourse;

    @Before
    public void cleanUp() {
        courseRepository.deleteAll();
    }

    @Given("the following courses exist:")
    public void theFollowingCoursesExist(DataTable dataTable) {
        List<Map<String, String>> courses = dataTable.asMaps();
        for (Map<String, String> course : courses) {
            Course newCourse = new Course();
            newCourse.setName(course.get("name"));
            newCourse.setCode(course.get("code"));
            newCourse.setCredits(Integer.parseInt(course.get("credits")));
            courseService.saveCourse(newCourse);
        }
    }

    @When("I create a course with name {string}, code {string} and credits {int}")
    public void iCreateACourseWithNameCodeAndCredits(String name, String code, Integer credits) {
        Course course = new Course();
        course.setName(name);
        course.setCode(code);
        course.setCredits(credits);
        foundCourse = courseService.saveCourse(course);
    }

    @When("I search for a course with code {string}")
    public void iSearchForACourseWithCode(String code) {
        Optional<Course> course = courseService.findCourseByCode(code);
        foundCourse = course.orElse(null);
    }

    @When("I enroll student {string} in course {string}")
    public void iEnrollStudentInCourse(String email, String code) {
        Optional<Student> studentOpt = studentService.findStudentByEmail(email);
        Optional<Course> courseOpt = courseService.findCourseByCode(code);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();

            enrollStudentInCourseService.enrollStudentInCourse(student, course);
        }
    }

    @Then("the course with code {string} should exist")
    public void theCourseWithCodeShouldExist(String code) {
        Optional<Course> course = courseService.findCourseByCode(code);
        assertTrue(course.isPresent(), "Course with code " + code + " should exist");
    }

    @Then("the course should have name {string}")
    public void theCourseShouldHaveName(String name) {
        assertNotNull(foundCourse, "Course should not be null");
        assertEquals(name, foundCourse.getName(), "Course should have name " + name);
    }

    @Then("the course should have {int} credits")
    public void theCourseShouldHaveCredits(Integer credits) {
        assertNotNull(foundCourse, "Course should not be null");
        assertEquals(credits, foundCourse.getCredits(), "Course should have " + credits + " credits");
    }

    @Then("I should find a course")
    public void iShouldFindACourse() {
        assertNotNull(foundCourse, "Course should be found");
    }

    @Then("student {string} should be enrolled in course {string}")
    public void studentShouldBeEnrolledInCourse(String email, String code) {
        Optional<Student> studentOpt = studentService.findStudentByEmail(email);
        Optional<Course> courseOpt = courseService.findCourseByCode(code);

        assertTrue(studentOpt.isPresent(), "Student with email " + email + " should exist");
        assertTrue(courseOpt.isPresent(), "Course with code " + code + " should exist");

        Student student = studentOpt.get();
        Course course = courseOpt.get();
        Set<Course> enrolledCourses = studentService.getEnrolledCourses(student);
        var codes = enrolledCourses.stream().map(Course::getCode).toList();
        assertTrue(codes.contains(course.getCode()),
                "Student should be enrolled in course " + code);
    }

    @Then("student {string} should be enrolled in {int} courses")
    public void studentShouldBeEnrolledInCourses(String email, Integer count) {
        Optional<Student> studentOpt = studentService.findStudentByEmail(email);
        assertTrue(studentOpt.isPresent(), "Student with email " + email + " should exist");
        Student student = studentOpt.get();

        Set<Course> enrolledCourses = studentService.getEnrolledCourses(student);
        assertEquals(count, enrolledCourses.size(),
                "Student should be enrolled in " + count + " courses");
    }
}
