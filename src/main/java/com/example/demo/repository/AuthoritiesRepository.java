package com.example.demo.repository;

import com.example.demo.entity.Authorities;
import com.example.demo.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    List<Authorities> findByUser(Users user);
}