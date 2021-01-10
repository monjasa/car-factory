package org.monjasa.carfactory.service.component;

import org.monjasa.carfactory.domain.CarBody;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarBodyFactory implements CarComponentFactory<CarBody> {

    private final AtomicInteger currentCarBodyId = new AtomicInteger(1);

    @Override
    public CarBody createCarComponent() {
        return new CarBody(currentCarBodyId.getAndIncrement());
    }
}
