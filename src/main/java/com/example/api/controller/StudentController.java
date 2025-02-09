package com.example.api.controller;

import com.example.api.model.Student;
import com.example.api.repository.StudentRepository;
import com.example.api.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/register/department")
    public ResponseEntity<?> registerDepartment(@RequestParam Long departmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Student not found"));
        student.setDepartment(departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found")));
        studentRepository.save(student);
        return ResponseEntity.ok("Department registered successfully");
    }

    // Additional endpoints for course registration and unregistration can be added here
}