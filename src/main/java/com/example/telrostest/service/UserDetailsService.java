package com.example.telrostest.service;


import com.example.telrostest.dto.UserDetailsDTO;
import com.example.telrostest.entity.User;
import com.example.telrostest.entity.UserDetails;
import com.example.telrostest.exception.NoValidData;
import com.example.telrostest.exception.UserNotFoundException;
import com.example.telrostest.mapper.UserDetailsMapper;
import com.example.telrostest.repository.UserDetailsRepository;
import com.example.telrostest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    private final UserService userService;


    public List<UserDetailsDTO> getDetails(UUID userId) {
        userService.findById(userId);
        List<UserDetails> detailsByUserId = getDetailsByUserId(userId);
        log.info("Получение контактной информации пользователя с ID: {}", userId);
        return detailsByUserId.stream()
                .map(UserDetailsMapper::toDTO)
                .toList();
    }

    public UUID addUserDetails(UUID userId, UserDetailsDTO userDetailsDTO) {
        if (userDetailsDTO == null) {
            throw new NoValidData(Utils.DETAILS_DTO_MESSAGE);
        }
        User user = userService.findById(userId);
        UserDetails userDetails = UserDetailsMapper.toEntity(userDetailsDTO);
        userDetails.setUser(user);
        UUID userDetailsId = userDetailsRepository.save(userDetails).getId();
        log.info("Добавление контактной информации пользователя  с ID: {}", userDetailsId);
        return userDetailsId;
    }

    public void patchDetails(UUID userDetailsId, UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = getDetailsById(userDetailsId);
        log.info("Обновление контактной информации пользователя с ID: {}", userDetailsId);
        Optional.ofNullable(userDetailsDTO.getEmail())
                .ifPresent(userDetails::setEmail);
        Optional.ofNullable(userDetailsDTO.getMobilePhone())
                .ifPresent(userDetails::setMobilePhone);
        userDetailsRepository.save(userDetails);
        log.info("Контактная информация пользователя с ID: {} обновлена", userDetailsId);
    }

    public void deleteDetails(UUID id) {
        UserDetails detailsById = getDetailsById(id);
        log.info("Удаление контактной информации пользователя с ID: {}", detailsById.getUser().getId());
        userDetailsRepository.delete(detailsById);
        log.info("Контактная информация пользователя с ID: {} удалена", detailsById.getUser().getId());
    }

    private List<UserDetails> getDetailsByUserId(UUID userId) {
        return userDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(Utils.DETAILS_NOT_FOUND_WITH_ID));
    }

    private UserDetails getDetailsById(UUID id) {
        return userDetailsRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(Utils.DETAILS_NOT_FOUND_WITH_ID));
    }
}
