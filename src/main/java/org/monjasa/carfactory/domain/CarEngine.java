package org.monjasa.carfactory.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class CarEngine implements CarComponent {
    @NonNull private int id;
}
