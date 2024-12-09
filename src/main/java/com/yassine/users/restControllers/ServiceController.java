package com.yassine.users.restControllers;

import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;
import com.yassine.users.service.ServiceService;
import com.yassine.users.service.UserService;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
	UserService userService;

    @PostMapping("/addService")
    public Services addService(@RequestBody Services service) {
        return serviceService.addService(service);
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
    @PostMapping("/loginn")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    try {
	        User user = userService.authenticateUser(loginRequest);
	        return ResponseEntity.ok(user);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}

}
