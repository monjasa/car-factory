package org.monjasa.carfactory.model.warehouse;

import java.util.concurrent.TimeUnit;

public interface ScheduledWarehouse {
    void scheduleWarehouseTask(Runnable task, long delay, TimeUnit timeUnit);
}
