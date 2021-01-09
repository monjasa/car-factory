package org.monjasa.carfactory.service;

import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import org.monjasa.carfactory.model.MainModel;
import org.monjasa.carfactory.util.ObservableBlockingQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MainService {

    private MainModel mainModel;

    @Value("${storage-capacity.engine}")
    @Getter
    private int engineStorageCapacity;

    public MainModel loadMainModel() {

        if (mainModel == null) {
            ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(engineStorageCapacity);

            mainModel = new MainModel(
                    new SimpleIntegerProperty(engineStorageCapacity),
                    new ObservableBlockingQueue<>(arrayBlockingQueue)
            );

            ExecutorService executor = Executors.newCachedThreadPool(runnable -> {
                Thread thread = Executors.defaultThreadFactory().newThread(runnable);
                thread.setDaemon(true);
                return thread;
            });

            executor.shutdown();
        }

        return mainModel;
    }
}
