package org.monjasa.carfactory.model.warehouse;

import org.monjasa.carfactory.domain.Product;
import org.monjasa.carfactory.model.observer.WarehouseObserver;
import org.monjasa.carfactory.util.DaemonThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ObservableScheduledProductWarehouse<T extends Product> extends AbstractProductWarehouse<T> implements ScheduledWarehouse, ObservableWarehouse {

    private final List<WarehouseObserver> warehouseObservers;
    private final ScheduledExecutorService executorService;

    public ObservableScheduledProductWarehouse(int warehouseCapacity) {

        super(warehouseCapacity);

        this.warehouseObservers = new ArrayList<>();
        this.executorService = new ScheduledThreadPoolExecutor(1,  new DaemonThreadFactory());
    }

    @Override
    public T takeProduct() throws InterruptedException {
        T product = super.takeProduct();
        notifyObservers();
        return product;
    }

    @Override
    public void scheduleWarehouseTask(Runnable task, long delay, TimeUnit timeUnit) {
        executorService.schedule(task, delay, timeUnit);
    }

    @Override
    public void subscribeObserver(WarehouseObserver warehouseObserver) {
        warehouseObservers.add(warehouseObserver);
    }

    @Override
    public void unsubscribeObserver(WarehouseObserver warehouseObserver) {
        warehouseObservers.remove(warehouseObserver);
    }

    @Override
    public void notifyObservers() {
        warehouseObservers.forEach(WarehouseObserver::update);
    }
}
