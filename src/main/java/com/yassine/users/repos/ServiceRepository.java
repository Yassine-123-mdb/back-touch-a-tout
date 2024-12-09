package com.yassine.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;

import java.util.List;

	public interface ServiceRepository extends JpaRepository<Services, Long> {
	    List<Services> findByUser(User user); // Trouver les services en fonction de l'utilisateur
	}

