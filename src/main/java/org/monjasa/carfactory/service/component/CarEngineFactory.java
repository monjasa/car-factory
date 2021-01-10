package org.monjasa.carfactory.service.component;

import org.monjasa.carfactory.domain.CarEngine;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarEngineFactory implements CarComponentFactory<CarEngine> {

    private final AtomicInteger currentCarEngineId = new AtomicInteger(1);

    @Override
    public CarEngine createCarComponent() {
        return new CarEngine(currentCarEngineId.getAndIncrement());
    }
}
