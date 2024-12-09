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
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
	UserService userService;

    // Ajouter un service pour un utilisateur
    @PostMapping("/add")
    public Services addService(@RequestBody Services service, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        service.setUser(user);
        return serviceService.addService(service);
    }

    // Récupérer tous les services
    @GetMapping("/all")
    public List<Services> getAllServices() {
        return serviceService.getAllServices();
    }

    // Récupérer les services d'un utilisateur
    @GetMapping("/user/{userId}")
    public List<Services> getServicesByUser(@PathVariable Long userId) {
        return serviceService.getServicesByUserId(userId);
    }

    // Mettre à jour un service
    @PutMapping("/{id}")
    public Services updateService(@PathVariable Long id, @RequestBody Services updatedService) {
        return serviceService.updateService(id, updatedService);
    }

    // Supprimer un service
    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
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
