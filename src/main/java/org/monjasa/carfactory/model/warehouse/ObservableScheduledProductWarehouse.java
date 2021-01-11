package org.monjasa.carfactory.model.warehouse;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.monjasa.carfactory.domain.Product;
import org.monjasa.carfactory.model.dealer.ProductDealer;
import org.monjasa.carfactory.model.observer.WarehouseObserver;
import org.monjasa.carfactory.util.DaemonThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Log4j2
public class ObservableScheduledProductWarehouse<T extends Product> extends AbstractProductWarehouse<T> implements ObservableWarehouse, ScheduledWarehouse {

    private final List<WarehouseObserver> warehouseObservers;
    private final ScheduledExecutorService executorService;

    private final ObservableList<Pair<T, ProductDealer<T>>> consumedProductDetails;

    public ObservableScheduledProductWarehouse(int warehouseCapacity) {

        super(warehouseCapacity);

        this.warehouseObservers = new ArrayList<>();
        this.executorService = new ScheduledThreadPoolExecutor(1,  new DaemonThreadFactory());

        this.consumedProductDetails = new ObservableListWrapper<>(new ArrayList<>());
        this.consumedProductDetails.addListener((ListChangeListener<Pair<T, ProductDealer<T>>>) change -> {
            change.next();
            change.getAddedSubList().forEach(pair -> {
                log.info(String.format("Dealer %s consumed a car %s", pair.getValue(), pair.getKey()));
            });
        });
    }

    @Override
    public T consumeProduct(ProductDealer<T> productDealer) throws InterruptedException {
        T product = super.consumeProduct(productDealer);
        consumedProductDetails.add(new Pair<>(product, productDealer));
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
