package com.example.telrostest.service;


import com.example.telrostest.dto.PhotoDTO;
import com.example.telrostest.entity.Photo;
import com.example.telrostest.entity.User;
import com.example.telrostest.exception.NoValidData;
import com.example.telrostest.exception.UserNotFoundException;
import com.example.telrostest.mapper.PhotoMapper;
import com.example.telrostest.repository.PhotoRepository;
import com.example.telrostest.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotosService {

    private final PhotoRepository photoRepository;

    private final UserService userService;


    public PhotoDTO getPhoto(UUID userId) {
        Photo photo = getPhotoByUserId(userId);
        log.info("Получение фото  пользователя с ID: {}", userId);
        return PhotoMapper.toDTO(photo);
    }
    public UUID addPhoto(UUID userId, MultipartFile file) {
        byte[] photoFile = checkMultipartFile(file);
        User user = userService.findById(userId);
        Photo photo = Photo.builder().
                photo(photoFile).
                user(user).
                build();
        UUID photoId = photoRepository.save(photo).getId();
        log.info("Добавлена новая фотография с ID: {}", photoId);
        return photoId;
    }

    public void putPhoto(UUID userId, MultipartFile file) {
        byte[] photoFile = checkMultipartFile(file);
        log.info("Обновление фото пользователя с ID: {}", userId);
        Photo photo = getPhotoByUserId(userId);
        photo.setPhoto(photoFile);
        photoRepository.save(photo);
        log.info("Пользователь с ID: {} обновлен", userId);
    }

    public void deleteUser(UUID userId) {
        log.info("Удаление фото пользователя с ID: {}", userId);
        Photo photoByUserId = getPhotoByUserId(userId);
        photoRepository.delete(photoByUserId);
        log.info("Фото пользователя с ID: {} удалено", userId);
    }

    private Photo getPhotoByUserId(UUID userId) {
        return photoRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(Utils.PHOTO_NOT_FOUND_WITH_ID));
    }

    private byte[] checkMultipartFile(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            throw new NoValidData(Utils.PHOTO_IS_EMPTY_MESSAGE);
        }

        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.equals("image/jpeg")) {
            throw new NoValidData(Utils.PHOTO_IS_NOT_JPEG_MESSAGE);
        }

        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(Utils.EXCEPTION_READ_FILE, e);
        }
    }
}


