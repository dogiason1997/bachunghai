package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.entity.Authorities;
import com.example.demo.entity.Users;
import com.example.demo.repository.AuthoritiesRepository;
import com.example.demo.repository.UserRepository;

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
    
    public Users createUser(CreateUserDTO createUserDTO){

        if (userRepository.existsByUsername(createUserDTO.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại trong hệ thống!");
        }
        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại trong hệ thống!");
        }

        Users user = new Users();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));        
        user.setFullName(createUserDTO.getFullName());
        user.setEmail(createUserDTO.getEmail());
        user.setEnabled(createUserDTO.getEnabled() != null ? createUserDTO.getEnabled() : true);
        
        Users savedUser = userRepository.save(user);



        List<String> authorities = createUserDTO.getAuthorities();
       if (authorities == null || authorities.isEmpty()) {
        authorities = List.of("MEMBER"); // hoặc Collections.singletonList("MEMBER")
       }
       for (String auth : authorities) {
       Authorities authority = new Authorities();
       authority.setAuthority(auth);
       authority.setUser(savedUser);
       authoritiesRepository.save(authority);
}

        // Authorities authority = new Authorities();
        // authority.setAuthority(createUserDTO.getAuthority() != null ? createUserDTO.getAuthority() : "MEMBER");
        // authority.setUser(savedUser);
        // authoritiesRepository.save(authority);

        return savedUser;
    
    
    }

    // public Page<CreateUserDTO> getAllUsersWithAuthorities(Pageable pageable) {
    //     return userRepository.findAllUsersWithAuthorities(pageable);
    // }

    public Page<CreateUserDTO> getAllUsersWithAuthorities(Pageable pageable) {
        Page<Users> usersPage = userRepository.findAllUsersWithAuthorities(pageable);
        return usersPage.map(user -> {
            List<String> authorities = user.getAuthorities()
                .stream()
                .map(Authorities::getAuthority)
                .collect(Collectors.toList());
            return new CreateUserDTO(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getEnabled(),
                authorities
            );
        });
    }

    public Optional<CreateUserDTO> getUserById(Integer id) {
        Optional<Users> userOpt = userRepository.findUserById(id);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            List<String> authorities = user.getAuthorities()
                                          .stream()
                                          .map(Authorities::getAuthority)
                                          .collect(Collectors.toList());
            CreateUserDTO dto = new CreateUserDTO(
                user.getUsername(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getEnabled(),
                authorities
            );
            return Optional.of(dto);
        }
        return Optional.empty();
    }


    // Xóa nhân viên
public void deleteUser(Integer id) {
    // Tìm theo id -> Lấy danh sach quyen -> XOa tung quyen -> xóa useruser 
    Optional<Users> userOpt = userRepository.findById(id);
    if (!userOpt.isPresent()) {
        throw new IllegalArgumentException("User không tồn tại!");
    }
    // Xóa quyền trước (nếu có ràng buộc foreign key)
    Users user = userOpt.get();
    List<Authorities> authorities = user.getAuthorities();
    if (authorities != null) {
        for (Authorities auth : authorities) {
            authoritiesRepository.delete(auth);
        }
    }
    userRepository.deleteById(id);
}

// Cập nhật nhân viên
public CreateUserDTO updateUser(Integer id, CreateUserDTO updateUserDTO) {
    // tim theo id -> kiểm tra trung -> cập nhật -> kấy danh sách quyền cũ -> xóa quyền cũ -> thêm quyền mới -> lưu lại user
    Optional<Users> userOpt = userRepository.findById(id);
    if (!userOpt.isPresent()) {
        throw new IllegalArgumentException("User không tồn tại!");
    }
    Users user = userOpt.get();

    // Kiểm tra nếu username/email mới đã tồn tại ở user khác
    if (!user.getUsername().equals(updateUserDTO.getUsername()) && userRepository.existsByUsername(updateUserDTO.getUsername())) {
        throw new IllegalArgumentException("Username đã tồn tại trong hệ thống!");
    }
    if (!user.getEmail().equals(updateUserDTO.getEmail()) && userRepository.existsByEmail(updateUserDTO.getEmail())) {
        throw new IllegalArgumentException("Email đã tồn tại trong hệ thống!");
    }

    user.setUsername(updateUserDTO.getUsername());
    if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
        user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
    }
    user.setFullName(updateUserDTO.getFullName());
    user.setEmail(updateUserDTO.getEmail());
    user.setEnabled(updateUserDTO.getEnabled() != null ? updateUserDTO.getEnabled() : true);

    // 1. Xóa hết các quyền cũ của user
    List<Authorities> oldAuthorities = authoritiesRepository.findByUser(user);
for (Authorities auth : oldAuthorities) {
    if (authoritiesRepository.existsById(auth.getId())) {
        authoritiesRepository.delete(auth);
    }
}
    // 2. Thêm các quyền mới
    List<String> newAuthorities = updateUserDTO.getAuthorities();
    if (newAuthorities == null || newAuthorities.isEmpty()) {
        newAuthorities = java.util.Collections.singletonList("MEMBER");
    }
    for (String auth : newAuthorities) {
        Authorities authority = new Authorities();
        authority.setAuthority(auth);
        authority.setUser(user);
        authoritiesRepository.save(authority);
    }

    Users savedUser = userRepository.save(user);

    // Trả về DTO mới
    List<String> authoritiesList = new java.util.ArrayList<String>();
    List<Authorities> authoritiesEntities = savedUser.getAuthorities();
    if (authoritiesEntities != null) {
        for (Authorities auth : authoritiesEntities) {
            authoritiesList.add(auth.getAuthority());
        }
    }
    return new CreateUserDTO(
        savedUser.getUsername(),
        savedUser.getPassword(),
        savedUser.getFullName(),
        savedUser.getEmail(),
        savedUser.getEnabled(),
        authoritiesList
    );
}
}
