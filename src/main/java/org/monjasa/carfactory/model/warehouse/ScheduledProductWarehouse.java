package org.monjasa.carfactory.model.warehouse;

import org.monjasa.carfactory.domain.Product;
import org.monjasa.carfactory.util.DaemonThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledProductWarehouse<T extends Product> extends AbstractProductWarehouse<T> implements ScheduledWarehouse {

    private final ScheduledExecutorService executorService;

    public ScheduledProductWarehouse(int warehouseCapacity) {

        super(warehouseCapacity);

        this.executorService = new ScheduledThreadPoolExecutor(1,  new DaemonThreadFactory());
    }

    @Override
    public void scheduleWarehouseTask(Runnable task, long delay, TimeUnit timeUnit) {
        executorService.schedule(task, delay, timeUnit);
    }
}
