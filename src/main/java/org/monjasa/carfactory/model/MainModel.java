package org.monjasa.carfactory.model;

import javafx.beans.property.IntegerProperty;
import lombok.Data;
import lombok.NonNull;
import org.monjasa.carfactory.util.ObservableBlockingQueue;

@Data
public class MainModel {
    @NonNull private IntegerProperty storageCapacity;
    @NonNull private ObservableBlockingQueue<String> observableBlockingQueue;
}
