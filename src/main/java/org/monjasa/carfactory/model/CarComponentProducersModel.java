package org.monjasa.carfactory.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.CarAccessory;
import org.monjasa.carfactory.domain.CarBody;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.service.factory.CarAccessoryFactory;
import org.monjasa.carfactory.service.factory.CarBodyFactory;
import org.monjasa.carfactory.service.factory.CarEngineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CarComponentProducersModel {

    private final CarEngineFactory carEngineFactory;
    private final CarBodyFactory carBodyFactory;
    private final CarAccessoryFactory carAccessoryFactory;

    @Value("${storage.engine.capacity}")
    private int engineStorageCapacity;
    @Value("${storage.body.capacity}")
    private int bodyStorageCapacity;
    @Value("${storage.accessory.capacity}")
    private int accessoryStorageCapacity;

    @Value("${producers.engine.count}")
    private int engineProducersCount;
    @Value("${producers.body.count}")
    private int bodyProducersCount;
    @Value("${producers.accessory.count}")
    private int accessoryProducersCount;

    @Getter private CarComponentStorage<CarEngine> carEngineStorage;
    @Getter private CarComponentStorage<CarBody> carBodyStorage;
    @Getter private CarComponentStorage<CarAccessory> carAccessoryStorage;

    @Getter private List<CarComponentProducer<? extends CarComponent>> carComponentProducers;

    @PostConstruct
    private void initializeProducers() {

        this.carEngineStorage = new CarComponentStorage<>(
                engineStorageCapacity,
                new ArrayBlockingQueue<>(engineStorageCapacity)
        );

        this.carBodyStorage = new CarComponentStorage<>(
                bodyStorageCapacity,
                new ArrayBlockingQueue<>(bodyStorageCapacity)
        );

        this.carAccessoryStorage = new CarComponentStorage<>(
                accessoryStorageCapacity,
                new ArrayBlockingQueue<>(accessoryStorageCapacity)
        );

        this.carComponentProducers = new ArrayList<>();

        IntStream.range(0, engineProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(carEngineFactory, carEngineStorage))
                .forEach(this.carComponentProducers::add);
        IntStream.range(0, bodyProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(carBodyFactory, carBodyStorage))
                .forEach(this.carComponentProducers::add);
        IntStream.range(0, accessoryProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(carAccessoryFactory, carAccessoryStorage))
                .forEach(this.carComponentProducers::add);

        this.carComponentProducers.forEach(CarComponentProducer::startProducing);
    }
}
