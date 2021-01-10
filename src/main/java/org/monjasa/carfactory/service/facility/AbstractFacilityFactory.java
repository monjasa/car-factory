package org.monjasa.carfactory.service.facility;

import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.model.producer.CarComponentProducer;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.service.component.CarComponentFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface AbstractFacilityFactory<T extends CarComponent> {

    CarComponentFactory<T> getCarComponentFactory();
    ProductWarehouse<T> getProductWarehouse();
    List<CarComponentProducer<T>> getCarComponentProducers();

    default List<CarComponentProducer<T>> createCarComponentProducers(int carComponentProducersCount) {
        return IntStream.range(0, carComponentProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(getCarComponentFactory(), getProductWarehouse()))
                .collect(Collectors.toList());
    }
}
