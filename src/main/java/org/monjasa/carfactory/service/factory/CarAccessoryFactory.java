package org.monjasa.carfactory.service.factory;

import org.monjasa.carfactory.domain.CarAccessory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarAccessoryFactory implements CarComponentFactory<CarAccessory> {

    private static final AtomicInteger currentCarAccessoryId = new AtomicInteger(1);

    @Override
    public CarAccessory createCarComponent() {
        return new CarAccessory(currentCarAccessoryId.getAndIncrement());
    }
}
