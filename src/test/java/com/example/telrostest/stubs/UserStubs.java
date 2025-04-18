package com.example.telrostest.stubs;

import com.example.telrostest.dto.UserDTO;
import com.example.telrostest.entity.User;

import java.util.UUID;

public class UserStubs {

    public static UUID userId = UUID.fromString("fd8e3ec5-7262-4d28-99aa-83e1af73d60f");

    public static User getUserStubs() {
        return User.builder()
                .id(userId)
                .firstName("Сергей")
                .lastName("Морозов")
                .middleName("Сергеевич")
                .build();
    }

    public static UserDTO getTrueUserDTOStubs() {
        return UserDTO.builder()
                .id(userId)
                .firstName("Сергей")
                .lastName("Морозов")
                .middleName("Сергеевич")
                .build();
    }

    public static UserDTO getFakeUserDTOStubs() {
        return UserDTO.builder()
                .id(UUID.randomUUID())
                .firstName("Jonh")
                .lastName("Malkovich")
                .middleName("Fedorovich")
                .build();
    }

    public static String jsonUser() {
        return """
                {
                             "last_name":"Дмитриев",
                             "first_name":"Игорь",
                             "middle_name":"Алексеевич",
                             "birth_date":"1991-03-14"
                }
                """;

    }

    public static String notValidJsonUser() {
        return """
                {
                             "last_name":"Дмитриев",
                             "first_name":"Игорь",
                }
                """;

    }
}
