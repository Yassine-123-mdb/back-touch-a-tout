package com.yassine.users.restControllers;

import java.util.List;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yassine.users.entities.User;
import com.yassine.users.repos.UserRepository;
import com.yassine.users.service.UserService;

import com.yassine.users.service.register.RegistrationRequest;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRep;

	@RequestMapping(path = "all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userRep.findAll();
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegistrationRequest request) throws RoleNotFoundException {
	    try {
	        User user = userService.registerUser(request);
	        return ResponseEntity.ok(user);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User userRequest) {
	    // Récupérer l'utilisateur par nom d'utilisateur ou email
	    User user = userService.findUserByUsername(userRequest.getUsername());
	    if (user == null || !bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
	        return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passe incorrect !");
	    }
	    
	    // Générer un JWT
	    String token = userService.generateToken(user);
	    return ResponseEntity.ok(Map.of("token", token, "username", user.getUsername()));
	}



	@GetMapping("/verifyEmail/{token}")
	public User verifyEmail(@PathVariable("token") String token) {
		return userService.validateToken(token);
	}

	
}
