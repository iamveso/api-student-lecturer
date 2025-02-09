package com.example.api.controller;

import com.example.api.model.Course;
import com.example.api.model.Lecturer;
import com.example.api.repository.LecturerRepository;
import com.example.api.repository.DepartmentRepository;
import com.example.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lecturer")
public class LecturerController {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/create/course")
    public ResponseEntity<?> createCourse(@RequestParam String courseName, @RequestParam Long departmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Lecturer lecturer = lecturerRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Lecturer not found"));
        Course course = new Course();
        course.setName(courseName);
        course.setDepartment(departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found")));
        course.setLecturer(lecturer);
        courseRepository.save(course);
        return ResponseEntity.ok("Course created successfully");
    }

    // Additional endpoints for managing students in courses can be added here
}