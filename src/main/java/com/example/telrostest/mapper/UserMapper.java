package com.example.telrostest.mapper;

import com.example.telrostest.dto.UserDTO;
import com.example.telrostest.entity.User;

public class UserMapper {

    private UserMapper() {

    }

    public static UserDTO toDTO(User user) {
        return UserDTO.builder().id(user.getId()).
                lastName(user.getLastName()).
                firstName(user.getFirstName()).
                middleName(user.getMiddleName()).
                birthDate(user.getBirthDate()).
                build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder().
                lastName(userDTO.getLastName()).
                firstName(userDTO.getFirstName()).
                middleName(userDTO.getMiddleName()).
                birthDate(userDTO.getBirthDate()).
                build();
    }

}
