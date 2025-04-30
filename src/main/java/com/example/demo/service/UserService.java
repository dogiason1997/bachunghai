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
}
