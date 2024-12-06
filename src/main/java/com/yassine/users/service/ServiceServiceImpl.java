package com.yassine.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassine.users.entities.Services;
import com.yassine.users.repos.ServiceRepository;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public Services addService(Services service) {
        return serviceRepository.save(service);
    }
}
