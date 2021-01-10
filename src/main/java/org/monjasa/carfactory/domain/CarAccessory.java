package org.monjasa.carfactory.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class CarAccessory implements CarComponent {
    @NonNull private int id;
}
