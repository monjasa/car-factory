package org.monjasa.carfactory.model;

import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledProductWarehouse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CarWarehouseModel {

    @Value("${warehouse.car.capacity}")
    private int carWarehouseCapacity;

    @Bean
    public ProductWarehouse<Car> carWarehouse() {
        return new ScheduledProductWarehouse<>(carWarehouseCapacity);
    }
}
