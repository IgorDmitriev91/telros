package com.example.telrostest.mapper;

import com.example.telrostest.dto.PhotoDTO;
import com.example.telrostest.entity.Photo;

public class PhotoMapper {
    private PhotoMapper() {
    }

    public static PhotoDTO toDTO(Photo photos) {
        return PhotoDTO.builder().
                id(photos.getId()).
                photo(photos.getPhoto()).

                build();
    }

    public static Photo toEntity(PhotoDTO photoDTO) {
        return Photo.builder().
                id(photoDTO.getId()).
                photo(photoDTO.getPhoto()).
                build();
    }
}
