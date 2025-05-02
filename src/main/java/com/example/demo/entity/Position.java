package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_code", length = 10, nullable = false, unique = true)
    private String positionCode;

    @Column(name = "position_name", length = 100, nullable = false)
    private String positionName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private List<Users> users;
} 