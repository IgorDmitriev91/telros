package com.example.telrostest.controller;


import com.example.telrostest.dto.SuccessMessage;
import com.example.telrostest.dto.UserDetailsDTO;
import com.example.telrostest.service.UserDetailsService;
import com.example.telrostest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("api/v1/details/{id}")
@RequiredArgsConstructor
@Slf4j
public class UserDetailsController {
    private final UserDetailsService userDetailsService;

    //В методе get, post используется id user-а, в patch, delete id userDetails
    @GetMapping()
    public ResponseEntity<List<UserDetailsDTO>> getDetails(@PathVariable UUID id) {
        List<UserDetailsDTO> userDetailsDTO = userDetailsService.getDetails(id);
        return userDetailsDTO.isEmpty() ? ResponseEntity.status(204)
                .body(userDetailsDTO) : ResponseEntity.ok().body(userDetailsDTO);
    }

    @PostMapping()
    public ResponseEntity<SuccessMessage> addDetails(@PathVariable UUID id, @RequestBody UserDetailsDTO userDetailsDTO) {
        UUID details = userDetailsService.addUserDetails(id, userDetailsDTO);
        String response = Utils.DETAILS_URL + details;
        return ResponseEntity.created(URI.create(response))
                .body(new SuccessMessage(Utils.DETAILS_SUCCESS_MESSAGE_CREATED));
    }

    @PatchMapping()
    public ResponseEntity<SuccessMessage> patchDetails(@PathVariable UUID id, @RequestBody UserDetailsDTO userDetailsDTO) {
        userDetailsService.patchDetails(id,userDetailsDTO);
        return ResponseEntity.ok().body(new SuccessMessage(Utils.DETAILS_SUCCESS_MESSAGE_UPDATED));
    }

    @DeleteMapping()
    public ResponseEntity<SuccessMessage> deleteDetails(@PathVariable UUID id) {
        userDetailsService.deleteDetails(id);
        return ResponseEntity.ok().body(new SuccessMessage(Utils.DETAILS_SUCCESS_MESSAGE_DELETED));
    }
}

