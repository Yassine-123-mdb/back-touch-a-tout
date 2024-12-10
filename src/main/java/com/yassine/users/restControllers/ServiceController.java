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
    private UserService userService;

    /**
     * Ajout d'un service pour un utilisateur spécifique
     * @param userId L'ID de l'utilisateur
     * @param service Le service à ajouter
     * @return La réponse avec le service ajouté
     */
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addService(
            @PathVariable("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("category") String category,
            @RequestParam(value = "duration", required = false) Integer duration,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Vérification si l'utilisateur existe
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.badRequest().body("Utilisateur non trouvé");
            }

            // Gestion de l'image
            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                String uploadDir = "uploads/images/"; // Dossier où les images seront enregistrées
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs(); // Crée le répertoire s'il n'existe pas
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                File destFile = new File(uploadDir + fileName);
                image.transferTo(destFile);
                imagePath = uploadDir + fileName; // Chemin de l'image sauvegardée
            }

            // Création du service
            Services service = new Services();
            service.setTitle(title);
            service.setDescription(description);
            service.setPrice(price);
            service.setCategory(category);
            service.setDuration(duration);
            service.setNotes(notes);
            service.setImage(imagePath); // Ajout du chemin de l'image
            service.setUser(user);

            // Enregistrement du service
            Services savedService = serviceService.addService(userId, service);
            return ResponseEntity.ok(savedService);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors du téléchargement de l'image");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'ajout du service");
        }
    }


    /**
     * Récupérer tous les services associés à un utilisateur
     * @param userId L'ID de l'utilisateur
     * @return Liste des services de l'utilisateur
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Services>> getServicesByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(serviceService.getServicesByUser(userId));
    }

    /**
     * Mise à jour d'un service
     * @param serviceId L'ID du service
     * @param serviceDetails Les nouveaux détails du service
     * @return Le service mis à jour
     */
    @PutMapping("/update/{serviceId}")
    public ResponseEntity<Services> updateService(@PathVariable("serviceId") Long serviceId, @RequestBody Services serviceDetails) {
        return ResponseEntity.ok(serviceService.updateService(serviceId, serviceDetails));
    }

    /**
     * Suppression d'un service
     * @param serviceId L'ID du service
     * @return Message de succès
     */
    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<String> deleteService(@PathVariable("serviceId") Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.ok("Service deleted successfully!");
    }

    /**
     * Gestion de l'upload d'une image
     * @param file Le fichier image à télécharger
     * @return Le message de succès ou d'erreur
     */
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

    /**
     * Méthode de connexion
     * @param loginRequest Les informations de connexion
     * @return Réponse avec l'utilisateur connecté
     */
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
