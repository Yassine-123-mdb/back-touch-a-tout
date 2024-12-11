package com.yassine.users.service;

import com.yassine.users.entities.Services;
import com.yassine.users.entities.User;
import com.yassine.users.repos.ServiceRepository;
import com.yassine.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Services addService(Long userId, Services service) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        service.setUser(user);
        return serviceRepository.save(service);
    }

    @Override
    public List<Services> getServicesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return user.getServices();
    }

    @Override
    public Services updateService(Long serviceId, Services serviceDetails) {
        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));
        service.setTitle(serviceDetails.getTitle());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        service.setCategory(serviceDetails.getCategory());
        service.setDuration(serviceDetails.getDuration());
        service.setNotes(serviceDetails.getNotes());
        service.setImage(serviceDetails.getImage());
        return serviceRepository.save(service);
    }

    @Override
    public void deleteService(Long serviceId) {
        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + serviceId));
        serviceRepository.delete(service);
    }
    
    @Override
    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }
}
