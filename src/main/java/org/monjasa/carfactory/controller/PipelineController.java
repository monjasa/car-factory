package org.monjasa.carfactory.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@FxmlView
public class PipelineController {

    @FXML AnchorPane scene;

    @Setter
    private Pipeline pipeline = null;

    private final Map<Pipeline.Item, Circle> icons = new HashMap<>();

    Point2D beginPosition;
    Point2D endPosition;

    private static double ICON_SIZE = 10.0;

    private void initializeCoordinates(){
        beginPosition = new Point2D(
                ICON_SIZE/2 + scene.getLayoutX(),
                ICON_SIZE/2 + scene.getLayoutY()
        );

        endPosition = new Point2D(
                scene.getLayoutX() + scene.getPrefWidth() - ICON_SIZE/2 ,
                ICON_SIZE/2 + scene.getLayoutY()
        );
    }

    private Circle getIconForItem(Pipeline.Item item){
        Circle icon = icons.get(item);

        if(icon == null){
            Circle newIcon = new Circle();
            newIcon.setLayoutX(beginPosition.getX());
            newIcon.setLayoutY(beginPosition.getY());
            newIcon.setRadius(10);
            newIcon.setStroke(Color.rgb(240,0,0));
            newIcon.setFill(Color.rgb(240,128,128));
            icons.put(item, newIcon);

            scene.getChildren().add(newIcon);
            return newIcon;
        }

        return icon;
    }

    private void removeIconForItem(Pipeline.Item item){
        var icon = icons.remove(item);
        if(icon != null){
            scene.getChildren().remove(icon);
        }
    }

    public void initialize(){
        initializeCoordinates();
        AnimationTimer timer = new AnimationTimer() {

            private long startTime ;

            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                pipeline.itemList().forEach(item ->{

                    var icon = getIconForItem(item);
                    //Pipeline.Point currentPosition = item.currentPosition(now);
                    float progress = item.currentProgress(now);

                    if(progress > 1.0) {
                        removeIconForItem(item);
                    }
                    else{

                        Point2D difference = endPosition.subtract(beginPosition);
                        Point2D currentPosition = beginPosition.add(difference.multiply(progress));
                        icon.setLayoutX(currentPosition.getX());
                        icon.setLayoutY(currentPosition.getY());
                    }

                });
            }
        };
        timer.start();
    }
}
