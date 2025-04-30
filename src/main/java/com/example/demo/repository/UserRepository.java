package com.example.demo.repository;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
//    @Query("SELECT new com.example.demo.dto.CreateUserDTO(u.username, u.password, u.fullName, u.email, u.enabled, a.authority) " +
//            "FROM Users u JOIN u.authorities a")
//    Page<CreateUserDTO> findAllUsersWithAuthorities(Pageable pageable);

   @Query("SELECT u FROM Users u")
   Page<Users> findAllUsersWithAuthorities(Pageable pageable);

   @Query("SELECT u FROM Users u WHERE u.idUser = :id")
   Optional<Users> findUserById(@Param("id") Integer id);

   boolean existsByUsername(String username);
   boolean existsByEmail(String email);
}
