package org.monjasa.carfactory.model.transport;

import lombok.Getter;
import org.monjasa.carfactory.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Scope("prototype")
public class Pipeline {
    static final long TRAVEL_TIME_MILLIS = 500;

    @Getter private final List<PipelineItem> items;

    public Pipeline(){
        items = new CopyOnWriteArrayList<>();
    }

    // TODO: Refactor
    public void addProduct(Product product){
        items.add(new PipelineItem(product));
    }
}
