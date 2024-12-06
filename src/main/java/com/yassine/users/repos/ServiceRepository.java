package com.yassine.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yassine.users.entities.Services;

public interface ServiceRepository extends JpaRepository<Services, Long> {
}
