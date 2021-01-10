package org.monjasa.carfactory.service.facility;

import lombok.Getter;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.model.producer.CarComponentProducer;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledProductWarehouse;
import org.monjasa.carfactory.service.component.CarComponentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CarEngineFacilityFactory implements AbstractFacilityFactory<CarEngine> {

    @Value("${warehouse.engine.capacity}")
    private int carEngineWarehouseCapacity;

    @Value("${producers.engine.count}")
    private int carEngineProducersCount;

    @Getter private CarComponentFactory<CarEngine> carComponentFactory;
    @Getter private ProductWarehouse<CarEngine> productWarehouse;
    @Getter private List<CarComponentProducer<CarEngine>> carComponentProducers;

    @PostConstruct
    private void initializeService() {
        productWarehouse = new ScheduledProductWarehouse<>(carEngineWarehouseCapacity);
        carComponentProducers = createCarComponentProducers(carEngineProducersCount);
    }

    @Autowired
    public void setCarComponentFactory(CarComponentFactory<CarEngine> carComponentFactory) {
        this.carComponentFactory = carComponentFactory;
    }
}
