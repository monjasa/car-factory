package org.monjasa.carfactory.model.warehouse;

import org.monjasa.carfactory.domain.Product;

public class SimpleProductWarehouse<T extends Product> extends AbstractProductWarehouse<T> {

    public SimpleProductWarehouse(int warehouseCapacity) {
        super(warehouseCapacity);
    }
}
