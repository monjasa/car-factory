package org.monjasa.carfactory.model.warehouse;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import org.monjasa.carfactory.domain.Product;
import org.monjasa.carfactory.util.ObservableBlockingQueue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class AbstractProductWarehouse<T extends Product> implements ProductWarehouse<T>, AuditableWarehouse {

    private final ObservableBlockingQueue<T> productQueue;
    private final ObservableList<T> productAudit;

    public AbstractProductWarehouse(int warehouseCapacity) {
        this.productQueue = new ObservableBlockingQueue<>(new ArrayBlockingQueue<>(warehouseCapacity));
        this.productAudit = new ObservableListWrapper<>(new ArrayList<>());
    }

    @Override
    public IntegerProperty capacityProperty() {
        return productQueue.capacityProperty();
    }

    @Override
    public IntegerBinding sizeBinding() {
        return Bindings.size(productQueue);
    }

    @Override
    public T takeProduct() throws InterruptedException {
        return productQueue.take();
    }

    @Override
    public void putProduct(T product) throws InterruptedException {
        productQueue.put(product);
        Platform.runLater(() -> productAudit.add(product));
    }

    @Override
    public IntegerBinding auditSizeBinding() {
        return Bindings.size(productAudit);
    }
}
