package org.monjasa.carfactory.model.warehouse;

import org.monjasa.carfactory.model.observer.WarehouseObserver;

public interface ObservableWarehouse {
    void subscribeObserver(WarehouseObserver warehouseObserver);
    void unsubscribeObserver(WarehouseObserver warehouseObserver);
    void notifyObservers();
}
