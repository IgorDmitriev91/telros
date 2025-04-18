package com.example.telrostest.repository;

import com.example.telrostest.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
   Optional<List<UserDetails>> findByUserId(UUID userId);
}
