package com.example.telrostest.mapper;

import com.example.telrostest.dto.UserDetailsDTO;
import com.example.telrostest.entity.UserDetails;

public class UserDetailsMapper {
    private UserDetailsMapper() {
    }

    public static UserDetailsDTO toDTO(UserDetails userDetails){
        return UserDetailsDTO
                .builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .mobilePhone(userDetails.getMobilePhone())
                .build();
    }

    public static UserDetails toEntity(UserDetailsDTO userDetailsDTO){
        return UserDetails
                .builder()
                .email(userDetailsDTO.getEmail())
                .mobilePhone(userDetailsDTO.getMobilePhone())
                .build();
    }
}
