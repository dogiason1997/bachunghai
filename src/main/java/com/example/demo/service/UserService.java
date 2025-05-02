package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.entity.Authorities;
import com.example.demo.entity.Users;
import com.example.demo.entity.Position;
import com.example.demo.entity.Department;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.AuthoritiesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserMapper userMapper;
    
    public CreateUserDTO createUser(CreateUserDTO createUserDTO) {
        // Validate username and email
        if (userRepository.existsByUsername(createUserDTO.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại trong hệ thống!");
        }
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống!");
        }

        // Find Position by name
        Position position = positionRepository.findByPositionName(createUserDTO.getPositionName());
        if (position == null) {
            throw new IllegalArgumentException("Không tìm thấy vị trí với tên: " + createUserDTO.getPositionName());
        }

        // Find Department by name
        Optional<Department> departmentOpt = departmentRepository.findByNameDepartment(createUserDTO.getNameDepartment());
        if (!departmentOpt.isPresent()) {
            throw new IllegalArgumentException("Không tìm thấy phòng ban với tên: " + createUserDTO.getNameDepartment());
        }
        Department department = departmentOpt.get();

        // Convert DTO to entity using mapper
        Users user = userMapper.toEntity(createUserDTO, position, department);
        // Encode password
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        
        // Save user
        Users savedUser = userRepository.save(user);

        // Handle authorities
        List<String> authorities = createUserDTO.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            authorities = List.of("MEMBER");
        }
        for (String auth : authorities) {
            Authorities authority = new Authorities();
            authority.setAuthority(auth);
            authority.setUser(savedUser);
            authoritiesRepository.save(authority);
        }

        // Convert back to DTO and return
        return userMapper.toDto(savedUser);
    }

    public Page<CreateUserDTO> getAllUsersWithAuthorities(Pageable pageable) {
        Page<Users> usersPage = userRepository.findAllUsersWithAuthorities(pageable);
        return usersPage.map(userMapper::toDto);
    }

    public Optional<CreateUserDTO> getUserById(Integer id) {
        Optional<Users> userOpt = userRepository.findUserById(id);
        return userOpt.map(userMapper::toDto);
    }

    public void deleteUser(Integer id) {
        Optional<Users> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User không tồn tại!");
        }
        Users user = userOpt.get();
        List<Authorities> authorities = user.getAuthorities();
        if (authorities != null) {
            for (Authorities auth : authorities) {
                authoritiesRepository.delete(auth);
            }
        }
        userRepository.deleteById(id);
    }

    public CreateUserDTO updateUser(Integer id, CreateUserDTO updateUserDTO) {
        Optional<Users> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User không tồn tại!");
        }
        Users existingUser = userOpt.get();

        // Validate username and email
        if (!existingUser.getUsername().equals(updateUserDTO.getUsername()) 
            && userRepository.existsByUsername(updateUserDTO.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại trong hệ thống!");
        }
        if (!existingUser.getEmail().equals(updateUserDTO.getEmail()) 
            && userRepository.existsByEmail(updateUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống!");
        }

        // Find Position by name
        Position position = positionRepository.findByPositionName(updateUserDTO.getPositionName());
        if (position == null) {
            throw new IllegalArgumentException("Không tìm thấy vị trí với tên: " + updateUserDTO.getPositionName());
        }

        // Find Department by name
        Optional<Department> departmentOpt = departmentRepository.findByNameDepartment(updateUserDTO.getNameDepartment());
        if (!departmentOpt.isPresent()) {
            throw new IllegalArgumentException("Không tìm thấy phòng ban với tên: " + updateUserDTO.getNameDepartment());
        }
        Department department = departmentOpt.get();

        // Update user fields
        Users userToUpdate = userMapper.toEntity(updateUserDTO, position, department);
        userToUpdate.setIdUser(existingUser.getIdUser());
        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            userToUpdate.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        } else {
            userToUpdate.setPassword(existingUser.getPassword());
        }

        // Update authorities
        List<Authorities> oldAuthorities = authoritiesRepository.findByUser(existingUser);
        for (Authorities auth : oldAuthorities) {
            authoritiesRepository.delete(auth);
        }

        Users savedUser = userRepository.save(userToUpdate);

        List<String> newAuthorities = updateUserDTO.getAuthorities();
        if (newAuthorities == null || newAuthorities.isEmpty()) {
            newAuthorities = java.util.Collections.singletonList("MEMBER");
        }
        for (String auth : newAuthorities) {
            Authorities authority = new Authorities();
            authority.setAuthority(auth);
            authority.setUser(savedUser);
            authoritiesRepository.save(authority);
        }

        // Convert to DTO and return
        return userMapper.toDto(savedUser);
    }
}
