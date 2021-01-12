package org.monjasa.carfactory.model.dealer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.NonNull;
import lombok.ToString;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.model.warehouse.ScheduledWarehouse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ToString(onlyExplicitlyIncluded = true)
public class CarDealer implements ProductDealer<Car> {

    private static final AtomicInteger CURRENT_CAR_DEALER_ID = new AtomicInteger(1);

    private Pipeline pipeline;

    public static CarDealer of(@NonNull ProductWarehouse<Car> carWarehouse, Pipeline pipeline) {
        return new CarDealer(CURRENT_CAR_DEALER_ID.getAndIncrement(), carWarehouse, pipeline);
    }

    @ToString.Include
    private final int id;

    private final ProductWarehouse<Car> carWarehouse;
    private final DoubleProperty requestingRate;

    private CarDealer(int id, @NonNull ProductWarehouse<Car> carWarehouse, Pipeline pipeline) {
        this.id = id;
        this.carWarehouse = carWarehouse;
        this.pipeline = pipeline;
        this.requestingRate = new SimpleDoubleProperty(1000);
    }

    @Override
    public void startRequesting() {

        final ScheduledWarehouse scheduledCarWarehouse = (ScheduledWarehouse) carWarehouse;

        Runnable requestingTask = new Runnable() {
            @Override
            public void run() {
                try {

                    pipeline.addProduct(carWarehouse.consumeProduct(CarDealer.this));
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    scheduledCarWarehouse.scheduleWarehouseTask(this, requestingRate.longValue(), TimeUnit.MILLISECONDS);
                }
            }
        };

        scheduledCarWarehouse.scheduleWarehouseTask(requestingTask, requestingRate.longValue(), TimeUnit.MILLISECONDS);
    }

    @Override
    public DoubleProperty requestingRateProperty() {
        return requestingRate;
    }
}
