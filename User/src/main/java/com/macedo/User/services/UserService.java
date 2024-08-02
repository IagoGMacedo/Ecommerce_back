package com.macedo.User.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.macedo.User.dtos.UserDTO;
import com.macedo.User.entities.Role;
import com.macedo.User.entities.User;
import com.macedo.User.repository.RoleRepository;
import com.macedo.User.repository.UserRepository;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO register(UserDTO usuario) {
        User user = extractUser(usuario);
        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);
        return toDTO(userRepository.save(user));
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(extractRoleNames(user.getRoles()))
                .build();
    }

    private User extractUser(UserDTO usuario) {
        User user = new User();
        user.setEmail(usuario.getEmail());
        user.setPassword(usuario.getPassword());

        List<String> rolesInUpperCase = Arrays.stream(usuario.getRoles())
                                          .map(role -> role.toUpperCase())
                                          .collect(Collectors.toList());

        user.setRoles(roleRepository.findByRoleNameIn(rolesInUpperCase));
        return user;
    }

    private String[] extractRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getRoleName().toUpperCase())
                .toArray(String[]::new);
    }



}
