package org.monjasa.carfactory.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.CarAccessory;
import org.monjasa.carfactory.domain.CarBody;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.model.producer.CarComponentProducer;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledProductWarehouse;
import org.monjasa.carfactory.service.factory.CarAccessoryFactory;
import org.monjasa.carfactory.service.factory.CarBodyFactory;
import org.monjasa.carfactory.service.factory.CarEngineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CarComponentProducersModel {

    private final CarEngineFactory carEngineFactory;
    private final CarBodyFactory carBodyFactory;
    private final CarAccessoryFactory carAccessoryFactory;

    @Value("${warehouse.engine.capacity}")
    private int engineWarehouseCapacity;
    @Value("${warehouse.body.capacity}")
    private int bodyWarehouseCapacity;
    @Value("${warehouse.accessory.capacity}")
    private int accessoryWarehouseCapacity;

    @Value("${producers.engine.count}")
    private int engineProducersCount;
    @Value("${producers.body.count}")
    private int bodyProducersCount;
    @Value("${producers.accessory.count}")
    private int accessoryProducersCount;

    @Getter private ProductWarehouse<CarEngine> carEngineWarehouse;
    @Getter private ProductWarehouse<CarBody> carBodyWarehouse;
    @Getter private ProductWarehouse<CarAccessory> carAccessoryWarehouse;

    @Getter private List<CarComponentProducer<? extends CarComponent>> carComponentProducers;

    @PostConstruct
    private void initializeModel() {

        this.carEngineWarehouse = new ScheduledProductWarehouse<>(engineWarehouseCapacity);
        this.carBodyWarehouse = new ScheduledProductWarehouse<>(bodyWarehouseCapacity);
        this.carAccessoryWarehouse = new ScheduledProductWarehouse<>(accessoryWarehouseCapacity);

        this.carComponentProducers = new ArrayList<>();

        IntStream.range(0, engineProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(carEngineFactory, carEngineWarehouse))
                .forEach(this.carComponentProducers::add);
        IntStream.range(0, bodyProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(carBodyFactory, carBodyWarehouse))
                .forEach(this.carComponentProducers::add);
        IntStream.range(0, accessoryProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(carAccessoryFactory, carAccessoryWarehouse))
                .forEach(this.carComponentProducers::add);

        this.carComponentProducers.forEach(CarComponentProducer::startProducing);
    }
}
