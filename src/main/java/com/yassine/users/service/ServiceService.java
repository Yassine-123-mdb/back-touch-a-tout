package com.yassine.users.service;

import com.yassine.users.entities.Services;

import java.util.List;

public interface ServiceService {
    Services addService(Long userId, Services service);
    List<Services> getServicesByUser(Long userId);
    Services updateService(Long serviceId, Services serviceDetails);
    void deleteService(Long serviceId);
    public List<Services> getAllServices();

}
