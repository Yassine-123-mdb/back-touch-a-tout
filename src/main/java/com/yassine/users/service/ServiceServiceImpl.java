package com.yassine.users.service;

import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;
import com.yassine.users.repos.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserService userService;

    @Override
    public Services addService(Services service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public List<Services> getServicesByUserId(Long userId) {
        User user = userService.getUserById(userId);  // Utilisez la méthode pour obtenir l'utilisateur
        return serviceRepository.findByUser(user);    // Passez l'utilisateur entier à la méthode repository
    }

    @Override
    public Services updateService(Long id, Services updatedService) {
        Services existingService = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        existingService.setTitle(updatedService.getTitle());
        existingService.setDescription(updatedService.getDescription());
        existingService.setPrice(updatedService.getPrice());
        existingService.setCategory(updatedService.getCategory());
        existingService.setDuration(updatedService.getDuration());
        existingService.setNotes(updatedService.getNotes());
        existingService.setImage(updatedService.getImage());
        return serviceRepository.save(existingService);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
