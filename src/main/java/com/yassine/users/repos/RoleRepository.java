package com.yassine.users.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yassine.users.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findById(Long id);
	List<Role> findAll();
	Role findByRole(String rolename);
	
}
