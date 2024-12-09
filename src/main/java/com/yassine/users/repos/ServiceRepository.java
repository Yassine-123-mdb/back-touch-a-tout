package com.yassine.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yassine.users.entities.Services;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Services, Long> {
    List<Services> findByUserId(Long userId); // Trouver tous les services d'un utilisateur
}
