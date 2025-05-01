package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column
    private String description;
    
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Article> articles;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Dispatch> dispatches;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<DispatchArrived> dispatchArriveds;
}
