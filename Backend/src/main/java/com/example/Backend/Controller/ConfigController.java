package com.example.Backend.Controller;

import com.example.Backend.model.Config;
import com.example.Backend.services.ConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configure")
public class ConfigController {
    @Autowired
    private ConfigServiceImpl configService;

    @PostMapping("/add")
    public String addConfig(@RequestBody Config config) {
        configService.saveConfiguration(config);
        return "Success";
    }

}
