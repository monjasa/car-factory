package org.monjasa.carfactory.model.transport;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.monjasa.carfactory.domain.Product;

import static org.monjasa.carfactory.model.transport.Pipeline.TRAVEL_TIME_MILLIS;

@RequiredArgsConstructor
@EqualsAndHashCode
public class PipelineItem {

    @Getter private final Product product;
    @Getter private final long creationTime;

    PipelineItem(Product product) {
        this.product = product;
        this.creationTime = System.currentTimeMillis();
    }

    public float currentProgress(long currentTime) {
        return (float) (currentTime - creationTime) / TRAVEL_TIME_MILLIS;
    }
}
