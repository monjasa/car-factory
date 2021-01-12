package org.monjasa.carfactory.model;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.warehouse.ObservableWarehouse;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@RequiredArgsConstructor
public class CarPlantModel {

    private final CarComponentProducersModel carComponentProducersModel;

    @Getter private final ProductWarehouse<Car> carWarehouse;

    @Getter private final LongProperty waitingTaskCount = new SimpleLongProperty();
    @Getter private final LongProperty completedTaskCount = new SimpleLongProperty();

    @Value("${constructors.construction-time}")
    private int carConstructionTime;


    private Pipeline pipeline;

    @Value("${constructors.count}")
    @Getter private int constructorsCount;

    private ThreadPoolExecutor executor;

    @PostConstruct
    private void initializeModel() {

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(constructorsCount,  new DaemonThreadFactory());

        ObservableWarehouse observableWarehouse = (ObservableWarehouse) carWarehouse;
        observableWarehouse.notifyObservers();
    }

    public void submitCarConstructionTask() {
        executor.submit(new CarConstructionTask());
        Platform.runLater(() -> waitingTaskCount.set(waitingTaskCount.get() + 1));
    }

    private class CarConstructionTask implements Runnable {
        @Override
        public void run() {

            Platform.runLater(() -> waitingTaskCount.set(waitingTaskCount.get() - 1));

            try {
                Car car = Car.builder()
                        .carEngine(carComponentProducersModel.getCarEngineFacilityFactory().getProductWarehouse().consumeProduct())
                        .carBody(carComponentProducersModel.getCarBodyFacilityFactory().getProductWarehouse().consumeProduct())
                        .carAccessory(carComponentProducersModel.getCarAccessoryFacilityFactory().getProductWarehouse().consumeProduct())
                        .build();
                Thread.sleep(carConstructionTime);
                carWarehouse.supplyProduct(car);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            Platform.runLater(() -> completedTaskCount.set(completedTaskCount.get() + 1));
        }
    }
}
