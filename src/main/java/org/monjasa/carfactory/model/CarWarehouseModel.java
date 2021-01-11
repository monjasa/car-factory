package org.monjasa.carfactory.model;

import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.observer.CarWarehouseObserver;
import org.monjasa.carfactory.model.warehouse.ObservableScheduledProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarWarehouseModel {

    private final CarWarehouseObserver carWarehouseObserver;

    @Value("${warehouse.car.capacity}")
    private int carWarehouseCapacity;

    @Bean
    public ProductWarehouse<Car> carWarehouse() {
        ObservableScheduledProductWarehouse<Car> carWarehouse = new ObservableScheduledProductWarehouse<>(carWarehouseCapacity);
        carWarehouse.subscribeObserver(carWarehouseObserver);
        return carWarehouse;
    }
}
