package org.monjasa.carfactory.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

public class Speed {

    @Getter
    private final IntegerProperty speedProperty;

    public Speed(int speedValue) {
        speedProperty = new SimpleIntegerProperty(speedValue);
    }
}
