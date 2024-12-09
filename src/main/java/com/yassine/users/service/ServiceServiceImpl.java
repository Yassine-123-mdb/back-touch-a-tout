package com.yassine.users.service;

import com.yassine.users.entities.Services;
import com.yassine.users.repos.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Services addService(Services service) {
        return serviceRepository.save(service);
    }

    
}
