package org.monjasa.carfactory.model.producer;

import javafx.beans.property.DoubleProperty;
import org.monjasa.carfactory.domain.Product;

public interface ProductProducer<T extends Product> {
    void startProducing();
    DoubleProperty producingRateProperty();
}
