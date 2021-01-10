package org.monjasa.carfactory.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.dealer.CarDealer;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CarDealersModel {

    @Value("${dealers.count}")
    private int dealersCount;

    @Getter private ProductWarehouse<Car> carWarehouse;
    @Getter private List<CarDealer> carDealers;

    @PostConstruct
    private void initializeModel() {

        this.carDealers = IntStream.range(0, dealersCount)
                .mapToObj(value -> new CarDealer(carWarehouse))
                .collect(Collectors.toList());

        this.carDealers.forEach(CarDealer::startRequesting);
    }

    @Autowired
    public void setCarWarehouse(ProductWarehouse<Car> carWarehouse) {
        this.carWarehouse = carWarehouse;
    }
}
