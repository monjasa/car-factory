package org.monjasa.carfactory.service.factory;

import org.monjasa.carfactory.domain.CarBody;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarBodyFactory implements CarComponentFactory<CarBody> {

    private static final AtomicInteger currentCarBodyId = new AtomicInteger(1);

    @Override
    public CarBody createCarComponent() {
        return new CarBody(currentCarBodyId.getAndIncrement());
    }
}
