package org.monjasa.carfactory.model;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.warehouse.AbstractProductWarehouse;
import org.monjasa.carfactory.model.warehouse.SimpleProductWarehouse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@RequiredArgsConstructor
public class CarPlantModel {

    private final CarComponentProducersModel carComponentProducersModel;

    @Getter private final LongProperty waitingTaskCount = new SimpleLongProperty();
    @Getter private final LongProperty completedTaskCount = new SimpleLongProperty();

    @Value("${constructors.count}")
    private int constructorsCount;

    @Value("${warehouse.car.capacity}")
    private int carWarehouseCapacity;

    @Value("${constructors.construction-time}")
    private int carConstructionTime;

    @Getter private AbstractProductWarehouse<Car> carWarehouse;

    private ThreadPoolExecutor executor;

    @PostConstruct
    private void initializeModel() {

        this.carWarehouse = new SimpleProductWarehouse<>(carWarehouseCapacity);

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(constructorsCount, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                submitCarConstructionTask();
            }
        }, 2500, 1000);
    }

    private void submitCarConstructionTask() {
        executor.submit(new CarConstructionTask());
        Platform.runLater(() -> waitingTaskCount.set(waitingTaskCount.get() + 1));
    }

    private class CarConstructionTask implements Runnable {
        @Override
        public void run() {

            Platform.runLater(() -> waitingTaskCount.set(waitingTaskCount.get() - 1));

            try {
                Car car = Car.builder()
                        .carEngine(carComponentProducersModel.getCarEngineWarehouse().takeProduct())
                        .carBody(carComponentProducersModel.getCarBodyWarehouse().takeProduct())
                        .carAccessory(carComponentProducersModel.getCarAccessoryWarehouse().takeProduct())
                        .build();
                Thread.sleep(carConstructionTime);
                carWarehouse.putProduct(car);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            Platform.runLater(() -> completedTaskCount.set(completedTaskCount.get() + 1));
        }
    }
}
