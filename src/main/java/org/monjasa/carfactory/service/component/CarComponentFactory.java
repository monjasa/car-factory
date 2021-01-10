package org.monjasa.carfactory.service.component;

import org.monjasa.carfactory.domain.CarComponent;

public interface CarComponentFactory<T extends CarComponent> {
     T createCarComponent();
}
