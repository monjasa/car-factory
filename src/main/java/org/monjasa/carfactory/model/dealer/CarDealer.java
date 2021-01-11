package org.monjasa.carfactory.model.dealer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledWarehouse;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CarDealer {

    @NonNull private final ProductWarehouse<Car> carWarehouse;

    private final DoubleProperty requestingRate = new SimpleDoubleProperty(1000);

    public void startRequesting() {

        final ScheduledWarehouse scheduledCarWarehouse = (ScheduledWarehouse) carWarehouse;

        Runnable requestingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    carWarehouse.takeProduct();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    scheduledCarWarehouse.scheduleWarehouseTask(this, requestingRate.longValue(), TimeUnit.MILLISECONDS);
                }
            }
        };

        scheduledCarWarehouse.scheduleWarehouseTask(requestingTask, requestingRate.longValue(), TimeUnit.MILLISECONDS);
    }

    public DoubleProperty requestingRateProperty() {
        return requestingRate;
    }
}
