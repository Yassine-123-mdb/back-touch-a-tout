package com.yassine.users.service;

import com.yassine.users.entities.Services;

import java.util.List;

public interface ServiceService {
    Services addService(Services service);
    List<Services> getAllServices();
    List<Services> getServicesByUserId(Long userId);
    Services updateService(Long id, Services service);
    void deleteService(Long id);
}
