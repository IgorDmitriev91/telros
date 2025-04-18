package com.example.telrostest.controller;

import com.example.telrostest.dto.PhotoDTO;
import com.example.telrostest.dto.SuccessMessage;
import com.example.telrostest.service.PhotosService;
import com.example.telrostest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/photos/{userId}")
@RequiredArgsConstructor
@Slf4j
public class PhotoController {

    private final PhotosService photosService;

   //фото работает только в формате jpeg
    @GetMapping()
    public ResponseEntity<byte[]> getPhoto(@PathVariable UUID userId) {
        PhotoDTO photoDTO = photosService.getPhoto(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(photoDTO.getPhoto());
    }

    @PostMapping()
    public ResponseEntity<SuccessMessage> addPhoto(@PathVariable UUID userId, @RequestBody MultipartFile file) {
        UUID photo = photosService.addPhoto(userId, file);
        String response = Utils.PHOTO_URL + photo;
        return ResponseEntity.created(URI.create(response))
                .body(new SuccessMessage(Utils.PHOTO_SUCCESS_MESSAGE_CREATED));
    }

    @PutMapping()
    public ResponseEntity<SuccessMessage> putPhoto(@PathVariable UUID userId, @RequestBody MultipartFile file) {
        photosService.putPhoto(userId, file);
        return ResponseEntity.ok().body(new SuccessMessage(Utils.PHOTO_SUCCESS_MESSAGE_UPDATED));
    }

    @DeleteMapping()
    public ResponseEntity<SuccessMessage> deletePhoto(@PathVariable UUID userId) {
        photosService.deleteUser(userId);
        return ResponseEntity.ok().body(new SuccessMessage(Utils.PHOTO_SUCCESS_MESSAGE_DELETED));
    }
}
