package org.monjasa.carfactory.model.producer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledWarehouse;
import org.monjasa.carfactory.service.component.CarComponentFactory;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CarComponentProducer<T extends CarComponent> {

    @NonNull private final CarComponentFactory<T> carComponentFactory;
    @NonNull private final ProductWarehouse<T> carComponentWarehouse;

    private final DoubleProperty producingRate = new SimpleDoubleProperty(1.0);

    public void startProducing() {

        final ScheduledWarehouse scheduledWarehouse = (ScheduledWarehouse) CarComponentProducer.this.carComponentWarehouse;

        Runnable producingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    carComponentWarehouse.putProduct(carComponentFactory.createCarComponent());
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    scheduledWarehouse.scheduleWarehouseTask(this, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
                }
            }
        };

        scheduledWarehouse.scheduleWarehouseTask(producingTask, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
    }

    public DoubleProperty producingRateProperty() {
        return producingRate;
    }
}
