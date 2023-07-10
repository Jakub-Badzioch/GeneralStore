package com.general.store.service;

import com.general.store.exception.CurrentUserNotFoundException;
import com.general.store.mapper.UserMapper;
import com.general.store.model.dao.impl.User;
import com.general.store.model.dto.entityanalogue.impl.UserDTO;
import com.general.store.repository.RoleRepository;
import com.general.store.repository.UserRepository;
import com.general.store.security.SecurityUtils;
import com.general.store.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    public UserDTO register(UserDTO userDTO) {
        final User user = userMapper.toEntity(userDTO);


    //  todo security:        user.setRoles(Collections.singletonList(roleRepository.findByNameEquals("USER").ifPresent(role -> )));
        roleRepository.findByName("USER").ifPresent(role -> user.setRoles(Collections.singletonList(role)));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        final Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", user.getFirstName());
        variables.put("lastName", user.getLastName());

        emailService.sendWithAttachment(user.getEmail(), "Register", variables, null, null);
        return userMapper.toDto(user);
    }

    public User findCurrentlyLoggedIn() {
        final String userName = SecurityUtils.getUserName();
        return userRepository.findByEmailEquals(userName)
                .orElseThrow(() -> new CurrentUserNotFoundException(userName));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User user, Long id) {
        final User userDb = userRepository.getReferenceById(id);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setPassword(user.getPassword());
        userDb.setEmail(user.getEmail());
        return userDb;
    }

    public User getById(Long id) {
        return userRepository.getReferenceById(id);
    }
}
