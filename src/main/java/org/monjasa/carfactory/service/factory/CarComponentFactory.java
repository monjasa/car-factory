package org.monjasa.carfactory.service.factory;

import org.monjasa.carfactory.domain.CarComponent;

public interface CarComponentFactory<T extends CarComponent> {
     T createCarComponent();
}
