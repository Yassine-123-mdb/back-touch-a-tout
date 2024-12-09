package com.yassine.users.restControllers;

import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;
import com.yassine.users.service.ServiceService;
import com.yassine.users.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
	UserService userService;

    @Autowired


    @PostMapping("/add/{userId}")
    public ResponseEntity<Services> addService(@PathVariable Long userId, @RequestBody Services service) {
        return ResponseEntity.ok(serviceService.addService(userId, service));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Services>> getServicesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(serviceService.getServicesByUser(userId));
    }

    @PutMapping("/update/{serviceId}")
    public ResponseEntity<Services> updateService(@PathVariable Long serviceId, @RequestBody Services serviceDetails) {
        return ResponseEntity.ok(serviceService.updateService(serviceId, serviceDetails));
    }

    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<String> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.ok("Service deleted successfully!");
    }

  
 // Ajout d'une méthode pour gérer l'upload d'image dans le backend (si nécessaire)
    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String uploadDir = "path/to/upload/dir"; // Changez ce chemin pour votre serveur
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);
            return "Image téléchargée avec succès";
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors du téléchargement de l'image";
        }
    }
    
    
    
    
    
    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    try {
	        User user = userService.authenticateUser(loginRequest);
	        return ResponseEntity.ok(user);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}

}
