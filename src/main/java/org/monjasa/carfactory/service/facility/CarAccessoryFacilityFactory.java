package org.monjasa.carfactory.service.facility;

import lombok.Getter;
import org.monjasa.carfactory.domain.CarAccessory;
import org.monjasa.carfactory.model.producer.CarComponentProducer;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledProductWarehouse;
import org.monjasa.carfactory.service.component.CarComponentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CarAccessoryFacilityFactory implements AbstractFacilityFactory<CarAccessory> {

    @Value("${warehouse.accessory.capacity}")
    private int carComponentWarehouseCapacity;

    @Value("${producers.accessory.count}")
    @Getter private int carComponentProducersCount;

    @Getter private CarComponentFactory<CarAccessory> carComponentFactory;
    @Getter private ProductWarehouse<CarAccessory> productWarehouse;
    @Getter private List<CarComponentProducer<CarAccessory>> carComponentProducers;
    @Getter private Pipeline pipeline;

    @Resource(name = "accessoryPipeline")
    public void setEnginePipeline(Pipeline enginePipeline) {
        this.pipeline = enginePipeline;
    }

    @PostConstruct
    private void initializeService() {
        productWarehouse = new ScheduledProductWarehouse<>(carComponentWarehouseCapacity);
        carComponentProducers = createCarComponentProducers(carComponentProducersCount);
    }

    @Autowired
    public void setCarComponentFactory(CarComponentFactory<CarAccessory> carComponentFactory) {
        this.carComponentFactory = carComponentFactory;
    }
}
