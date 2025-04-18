package com.example.telrostest.module;


import com.example.telrostest.dto.UserDTO;
import com.example.telrostest.entity.User;
import com.example.telrostest.exception.UserNotFoundException;
import com.example.telrostest.repository.UserRepository;
import com.example.telrostest.service.UserService;
import com.example.telrostest.stubs.UserStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.example.telrostest.stubs.UserStubs.userId;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserModuleTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void positiveTestGetUser() {
        UUID userId = UserStubs.userId;
        when(userRepository.findById(any())).thenReturn(Optional.of(UserStubs.getUserStubs()));

        UserDTO userDTO = userService.getUser(UserStubs.userId);

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals(userId, userDTO.getId());
        Assertions.assertEquals("Сергей", userDTO.getFirstName());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void negativeTestGetUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

    @Test
    void positiveTestAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(UserStubs.getUserStubs());

        UUID result = userService.addUser(UserStubs.getTrueUserDTOStubs());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userId, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testPatchUser() {
        User user = UserStubs.getUserStubs();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.patchUser(UserStubs.getTrueUserDTOStubs());

        Assertions.assertEquals("Сергей", user.getFirstName());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void negativeTestPatchUserNotFound() {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.patchUser(UserStubs.getTrueUserDTOStubs()));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void negativeTestDeleteUserNotFound() {

        when(userRepository.existsById(userId)).thenReturn(false);


        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    void testFindByIdUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void negativeTestGetUserWithNullId() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser(null));
    }
}

