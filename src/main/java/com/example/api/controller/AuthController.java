package com.example.api.controller;

import com.example.api.model.Student;
import com.example.api.dto.AuthRequest;
import com.example.api.dto.AuthResponse;
import com.example.api.model.Lecturer;
import com.example.api.repository.StudentRepository;
import com.example.api.repository.LecturerRepository;
import com.example.api.security.JwtUtil;
import com.example.api.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return ResponseEntity.ok("Student registered successfully");
    }

    @PostMapping("/register/lecturer")
    public ResponseEntity<?> registerLecturer(@RequestBody Lecturer lecturer) {
        lecturer.setPassword(passwordEncoder.encode(lecturer.getPassword()));
        lecturerRepository.save(lecturer);
        return ResponseEntity.ok("Lecturer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}