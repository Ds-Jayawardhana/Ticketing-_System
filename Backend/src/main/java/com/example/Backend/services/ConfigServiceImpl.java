package com.example.Backend.services;

import com.example.Backend.model.Config;
import com.example.Backend.repository.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigServices {

    @Autowired
    private ConfigRepo configRepo; // Inject ConfigRepo instance

    @Override
    public Config saveConfiguration(Config config) {
        return configRepo.save(config); // Use the instance to save
    }
    public Config getConfigs(){
        return configRepo.findFirstByOrderIdDsec()
            .orElse(new Config());
    }
}

