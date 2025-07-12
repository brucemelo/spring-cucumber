package com.brucemelo.springcucumber.steps;

import com.brucemelo.springcucumber.domain.Student;
import com.brucemelo.springcucumber.domain.StudentCourseRepository;
import com.brucemelo.springcucumber.domain.StudentRepository;
import com.brucemelo.springcucumber.service.StudentService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentSteps {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentService studentService;

    public StudentSteps(StudentRepository studentRepository,
                        StudentCourseRepository studentCourseRepository,
                        StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.studentService = studentService;
    }

    private Student foundStudent;

    @Before
    public void cleanUp() {
        studentCourseRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Given("the following students exist:")
    public void theFollowingStudentsExist(DataTable dataTable) {
        List<Map<String, String>> students = dataTable.asMaps();

        for (Map<String, String> student : students) {
            Student newStudent = new Student();
            newStudent.setName(student.get("name"));
            newStudent.setEmail(student.get("email"));
            studentService.saveStudent(newStudent);
        }
    }

    @When("I create a student with name {string} and email {string}")
    public void iCreateAStudentWithNameAndEmail(String name, String email) {
        var student = new Student();
        student.setName(name);
        student.setEmail(email);
        foundStudent = studentService.saveStudent(student);
    }

    @When("I search for a student with email {string}")
    public void iSearchForAStudentWithEmail(String email) {
        Optional<Student> student = studentService.findStudentByEmail(email);
        foundStudent = student.orElse(null);
    }

    @Then("the student with email {string} should exist")
    public void theStudentWithEmailShouldExist(String email) {
        Optional<Student> student = studentService.findStudentByEmail(email);
        assertTrue(student.isPresent(), "Student with email " + email + " should exist");
    }

    @Then("the student should have name {string}")
    public void theStudentShouldHaveName(String name) {
        assertNotNull(foundStudent, "Student should not be null");
        assertEquals(name, foundStudent.getName(), "Student should have name " + name);
    }

    @Then("I should find a student")
    public void iShouldFindAStudent() {
        assertNotNull(foundStudent, "Student should be found");
    }

    @Then("I should not find a student")
    public void iShouldNotFindAStudent() {
        assertNull(foundStudent, "Student should not be found");
    }
}
