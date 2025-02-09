package com.example.api.security;

import com.example.api.model.Student;
import com.example.api.model.Lecturer;
import com.example.api.repository.StudentRepository;
import com.example.api.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> student = studentRepository.findByUsername(username);
        if (student.isPresent()) {
            return student.get();
        }

        Optional<Lecturer> lecturer = lecturerRepository.findByUsername(username);
        if (lecturer.isPresent()) {
            return lecturer.get();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}