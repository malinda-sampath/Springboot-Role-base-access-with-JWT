package com.security.jwt_test.bootstrap;

import com.security.jwt_test.dtos.RegisterUserDto;
import com.security.jwt_test.entities.Role;
import com.security.jwt_test.entities.RoleEnum;
import com.security.jwt_test.entities.User;
import com.security.jwt_test.repositories.RoleRepository;
import com.security.jwt_test.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder)
    {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdmin();
    }

    private void createAdmin() {
        RegisterUserDto userDTO = new RegisterUserDto();
        userDTO.setFullName("SUPER ADMIN");
        userDTO.setEmail("malindasampath45@gmail.com");
        userDTO.setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}
