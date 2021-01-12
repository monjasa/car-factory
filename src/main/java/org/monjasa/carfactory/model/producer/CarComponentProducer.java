package org.monjasa.carfactory.model.producer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.domain.Product;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledWarehouse;
import org.monjasa.carfactory.service.component.CarComponentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CarComponentProducer<T extends CarComponent> implements ProductProducer<T> {

    @NonNull private final CarComponentFactory<T> carComponentFactory;
    @NonNull private final ProductWarehouse<T> carComponentWarehouse;
    @NonNull private final Pipeline pipeline = Pipeline.getInstance();

    private final DoubleProperty producingRate = new SimpleDoubleProperty(1.0);


    @Override
    public void startProducing() {

        final ScheduledWarehouse scheduledWarehouse = (ScheduledWarehouse) CarComponentProducer.this.carComponentWarehouse;

        Runnable producingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    CarComponent component = carComponentFactory.createCarComponent();
                    pipeline.addProduct(component);
                    carComponentWarehouse.supplyProduct((T) component, CarComponentProducer.this);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    scheduledWarehouse.scheduleWarehouseTask(this, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
                }
            }
        };

        scheduledWarehouse.scheduleWarehouseTask(producingTask, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
    }

    @Override
    public DoubleProperty producingRateProperty() {
        return producingRate;
    }
}
