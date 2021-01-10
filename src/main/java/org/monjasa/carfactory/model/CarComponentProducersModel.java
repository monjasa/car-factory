package org.monjasa.carfactory.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.model.producer.CarComponentProducer;
import org.monjasa.carfactory.service.facility.CarAccessoryFacilityFactory;
import org.monjasa.carfactory.service.facility.CarBodyFacilityFactory;
import org.monjasa.carfactory.service.facility.CarEngineFacilityFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CarComponentProducersModel {

    @Getter private final CarEngineFacilityFactory carEngineFacilityFactory;
    @Getter private final CarBodyFacilityFactory carBodyFacilityFactory;
    @Getter private final CarAccessoryFacilityFactory carAccessoryFacilityFactory;

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
