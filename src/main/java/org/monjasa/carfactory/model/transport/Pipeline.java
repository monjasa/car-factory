package org.monjasa.carfactory.model.transport;

import lombok.Getter;
import org.monjasa.carfactory.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Scope("prototype")
public class Pipeline<T extends Product> {

    private static Pipeline<? extends Product> instance;

    public static Pipeline<? extends Product> getInstance(){
        if(instance == null)
            instance = new Pipeline<>();

        return instance;
    }

    static final long TRAVEL_TIME_MILLIS = 5000;

    @Getter private final List<PipelineItem<T>> items;

    public Pipeline(){
        items = new CopyOnWriteArrayList<>();
    }

    // TODO: Refactor
    public void addProduct(T product){
        items.add(new PipelineItem<>(product));
    }
}
