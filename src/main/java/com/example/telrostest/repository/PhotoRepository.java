package com.example.telrostest.repository;

import com.example.telrostest.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    Optional<Photo> findByUserId(UUID userId);
}
