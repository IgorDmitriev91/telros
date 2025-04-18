package com.example.telrostest.controller;

import com.example.telrostest.dto.SuccessMessage;
import com.example.telrostest.dto.UserDTO;
import com.example.telrostest.service.UserService;
import com.example.telrostest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getClient(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping()
    public ResponseEntity<SuccessMessage> addUser(@RequestBody UserDTO userDTO) {
        UUID userId = userService.addUser(userDTO);
        String response = Utils.USER_URL + userId;
        return ResponseEntity.created(URI.create(response))
                .body(new SuccessMessage(Utils.USER_SUCCESS_MESSAGE_CREATED));
    }

    @PatchMapping()
    public ResponseEntity<SuccessMessage> patchUser(@RequestBody UserDTO userDTO) {
        userService.patchUser(userDTO);
        return ResponseEntity.ok().body(new SuccessMessage(Utils.USER_SUCCESS_MESSAGE_UPDATED));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SuccessMessage> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(new SuccessMessage(Utils.USER_SUCCESS_MESSAGE_DELETED));
    }
}