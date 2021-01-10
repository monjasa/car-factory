package org.monjasa.carfactory.model;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Data;
import lombok.NonNull;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.service.factory.CarComponentFactory;

@Data
public class CarComponentProducer<T extends CarComponent> {

    @NonNull private CarComponentFactory<T> carComponentFactory;
    @NonNull private CarComponentStorage<T> carComponentStorage;

    private DoubleProperty producingRate = new SimpleDoubleProperty(1.0);

    public void startProducing() {

        Runnable producingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    carComponentStorage.getObservableBlockingQueue().put(carComponentFactory.createCarComponent());
                    Platform.runLater(() -> {
                        IntegerProperty producedCarComponentsCount = carComponentStorage.getProducedCarComponentsCount();
                        producedCarComponentsCount.set(producedCarComponentsCount.getValue() + 1);
                    });
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    carComponentStorage.scheduleStorageTask(this, producingRate.multiply(1000).longValue());
                }
            }
        };

        carComponentStorage.scheduleStorageTask(producingTask, producingRate.multiply(1000).longValue());
    }
}
