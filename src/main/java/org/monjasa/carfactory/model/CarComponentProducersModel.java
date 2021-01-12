package org.monjasa.carfactory.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.CarAccessory;
import org.monjasa.carfactory.domain.CarBody;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.model.producer.CarComponentProducer;
import org.monjasa.carfactory.service.facility.AbstractFacilityFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CarComponentProducersModel {

    @Getter private final AbstractFacilityFactory<CarEngine> carEngineFacilityFactory;
    @Getter private final AbstractFacilityFactory<CarBody> carBodyFacilityFactory;
    @Getter private final AbstractFacilityFactory<CarAccessory> carAccessoryFacilityFactory;

    @PostConstruct
    private void initializeModel() {

        Stream.of(
                carEngineFacilityFactory.getCarComponentProducers(),
                carBodyFacilityFactory.getCarComponentProducers(),
                carAccessoryFacilityFactory.getCarComponentProducers()
        )
                .flatMap(Collection::stream)
                .forEach(CarComponentProducer::startProducing);
    }
}
