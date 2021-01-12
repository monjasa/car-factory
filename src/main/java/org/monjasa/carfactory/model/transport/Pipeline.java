package org.monjasa.carfactory.model.transport;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;
import org.monjasa.carfactory.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class Pipeline {

    static Pipeline instance = null;

    public static Pipeline getInstance(){
        if(instance == null)
            instance = new Pipeline();

        return instance;
    }

    static final long TRAVEL_TIME_MILLIS = 5000;

    private List<Item> items;

    public Pipeline(){
        items = new CopyOnWriteArrayList<>();
    }

    public List<Item> itemList(){
        return items;
    }

    public void addProduct(Product product){ // Refactor
        items.add(new Item(product));
    }


    @Data
    public static class Item{
        private final Product product;
        private final long creationTime;

        Item(Product product) {
            this.product = product;
            this.creationTime  = System.currentTimeMillis();
        }

        @Override
        public int hashCode() {
            return Objects.hash(product, creationTime);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return creationTime == item.creationTime && Objects.equals(product, item.product);
        }

        public float currentProgress(long currentTime){
            return (float)(currentTime - creationTime)/ TRAVEL_TIME_MILLIS;
        }

       /* public Point currentPosition(long currentTime){
            float k = (float)(currentTime - creationTime)/ TRAVEL_TIME_MILLIS;

            if(k > 1.f){
                return null;
            }

            return new Point(
                    begin.getX() + difference.getX() * k,
                    begin.getY() + difference.getY() * k
            );
        }*/

    }
}
