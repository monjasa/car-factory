package org.monjasa.carfactory.model.producer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Data;
import lombok.NonNull;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledWarehouse;
import org.monjasa.carfactory.service.factory.CarComponentFactory;

import java.util.concurrent.TimeUnit;

@Data
public class CarComponentProducer<T extends CarComponent> {

    @NonNull private CarComponentFactory<T> carComponentFactory;
    @NonNull private ProductWarehouse<T> carComponentWarehouse;

    private DoubleProperty producingRate = new SimpleDoubleProperty(1.0);

    public void startProducing() {

        Runnable producingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    carComponentWarehouse.putProduct(carComponentFactory.createCarComponent());
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    ((ScheduledWarehouse) carComponentWarehouse).scheduleWarehouseTask(this, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
                }
            }
        };

        ((ScheduledWarehouse) carComponentWarehouse).scheduleWarehouseTask(producingTask, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
    }
}
