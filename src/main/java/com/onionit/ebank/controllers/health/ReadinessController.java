package com.onionit.ebank.controllers.health;

import com.onionit.ebank.controllers.annotations.HealthController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@HealthController
@RequestMapping("readiness")
public class ReadinessController {
    @GetMapping
    public String check() {
        return "OK";
    }
}