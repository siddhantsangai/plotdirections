package com.locus.plotdirections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyReader {
    @Value("${application.plotdistance}")
    private double requiredMinDistance;

    @Value("${application.apikey}")
    private String apiKey;

    public double getRequiredMinDistance() {
        return requiredMinDistance;
    }

    public String getApiKey() {
        return apiKey;
    }
}
