package com.yassine.users.restControllers;

import com.yassine.users.entities.Services;
import com.yassine.users.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

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
}
