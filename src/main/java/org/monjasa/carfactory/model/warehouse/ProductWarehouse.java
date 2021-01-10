package org.monjasa.carfactory.model.warehouse;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import org.monjasa.carfactory.domain.Product;

public interface ProductWarehouse<T extends Product> {

    IntegerProperty capacityProperty();
    IntegerBinding sizeBinding();

    T takeProduct() throws InterruptedException;
    void putProduct(T product) throws InterruptedException;
}
