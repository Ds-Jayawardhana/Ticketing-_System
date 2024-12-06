package com.example.Backend.services;
import com.example.Backend.model.Config;

public interface ConfigServices {
    public Config saveConfiguration(Config config);

    public Config getConfig();
}
