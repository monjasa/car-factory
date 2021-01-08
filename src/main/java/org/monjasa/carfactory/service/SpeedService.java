package org.monjasa.carfactory.service;

import org.monjasa.carfactory.model.Speed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpeedService {

    @Value("${default-speed-value}")
    private int defaultSpeedValue;

    private Speed speed;

    public Speed getSpeed() {

        if (speed == null) {
            speed = new Speed(defaultSpeedValue);
        }

        return speed;
    }
}
