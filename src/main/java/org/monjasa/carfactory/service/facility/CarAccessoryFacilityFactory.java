package org.monjasa.carfactory.service.facility;

import lombok.Getter;
import org.monjasa.carfactory.domain.CarAccessory;
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
public class CarAccessoryFacilityFactory implements AbstractFacilityFactory<CarAccessory> {

    @Value("${warehouse.accessory.capacity}")
    private int carAccessoryWarehouseCapacity;

    @Value("${producers.accessory.count}")
    private int carAccessoryProducersCount;

    @Getter private CarComponentFactory<CarAccessory> carComponentFactory;
    @Getter private ProductWarehouse<CarAccessory> productWarehouse;
    @Getter private List<CarComponentProducer<CarAccessory>> carComponentProducers;

    @PostConstruct
    private void initializeService() {
        productWarehouse = new ScheduledProductWarehouse<>(carAccessoryWarehouseCapacity);
        carComponentProducers = createCarComponentProducers(carAccessoryProducersCount);
    }

    @Autowired
    public void setCarComponentFactory(CarComponentFactory<CarAccessory> carComponentFactory) {
        this.carComponentFactory = carComponentFactory;
    }
}