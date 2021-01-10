package org.monjasa.carfactory.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.util.ObservableBlockingQueue;

import java.util.concurrent.*;

@Data
public class CarComponentStorage<T extends CarComponent> {

    private IntegerProperty storageCapacity;
    private IntegerProperty producedCarComponentsCount;

    private ObservableBlockingQueue<T> observableBlockingQueue;
    private ScheduledExecutorService executorService;

    public CarComponentStorage(int storageCapacityValue, BlockingQueue<T> blockingQueue) {

        this.storageCapacity = new SimpleIntegerProperty(storageCapacityValue);
        this.producedCarComponentsCount = new SimpleIntegerProperty(0);

        this.observableBlockingQueue = new ObservableBlockingQueue<>(blockingQueue);

        this.executorService = new ScheduledThreadPoolExecutor(2, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
    }

    public void scheduleStorageTask(Runnable task, long delayInMilliseconds) {
        executorService.schedule(task, delayInMilliseconds, TimeUnit.MILLISECONDS);
    }
}
