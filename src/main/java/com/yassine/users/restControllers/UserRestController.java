package com.yassine.users.restControllers;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;
import com.yassine.users.repos.UserRepository;
import com.yassine.users.service.ServiceService;
import com.yassine.users.service.UserService;

import com.yassine.users.service.register.RegistrationRequest;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
	@Autowired
	UserService userService;
	
	@Autowired
    private ServiceService serviceService;

	@Autowired
	UserRepository userRep;

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userRep.findAll();
	}
	@GetMapping("/allService")
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
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


	@GetMapping("/verifyEmail/{token}")
	public User verifyEmail(@PathVariable("token") String token) {
		return userService.validateToken(token);
	}

	
}