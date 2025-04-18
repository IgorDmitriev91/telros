package com.example.telrostest.service;

import com.example.telrostest.dto.UserDTO;
import com.example.telrostest.entity.User;
import com.example.telrostest.exception.UserNotFoundException;
import com.example.telrostest.exception.NoValidData;
import com.example.telrostest.mapper.UserMapper;
import com.example.telrostest.repository.UserRepository;
import com.example.telrostest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    public UserDTO getUser(UUID id) {
        log.info("Получение пользователя с ID: {}", id);
        return UserMapper.toDTO(findById(id));
    }

    public UUID addUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new NoValidData(Utils.NULL_REQUEST_DTO_MESSAGE);
        }
        User user = UserMapper.toEntity(userDTO);
        UUID userId = userRepository.save(user).getId();
        log.info("Добавлен новый пользователь с ID: {}", userId);
        return userId;
    }

    public void patchUser(UserDTO userDTO) {
        log.info("Обновление пользователь с ID: {}", userDTO.getId());
        User user = findById(userDTO.getId());
        Optional.ofNullable(userDTO.getFirstName())
                .ifPresent(user::setFirstName);
        Optional.ofNullable(userDTO.getMiddleName())
                .ifPresent(user::setMiddleName);
        Optional.ofNullable(userDTO.getLastName())
                .ifPresent(user::setLastName);
        userRepository.save(user);
        log.info("Пользователь с ID: {} обновлен", user.getId());
    }

    public void deleteUser(UUID id) {
        log.info("Попытка удалить пользователя с ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(Utils.USER_NOT_FOUND_WITH_ID);
        }
        userRepository.deleteById(id);
        log.info("Пользователь с ID: {} удален", id);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(Utils.USER_NOT_FOUND_WITH_ID));
    }
}

