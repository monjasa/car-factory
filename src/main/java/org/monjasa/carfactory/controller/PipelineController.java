package org.monjasa.carfactory.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.monjasa.carfactory.model.transport.PipelineItem;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@FxmlView
public class PipelineController {

    private static final double ICON_SIZE = 10.0;

    @FXML StackPane stackPane;

    @Setter
    private Pipeline<CarEngine> pipeline;

    private final Map<PipelineItem<CarEngine>, Circle> icons = new HashMap<>();

    private Point2D beginPosition;
    private Point2D endPosition;

    private void initializeCoordinates() {
        beginPosition = new Point2D(
                ICON_SIZE / 2 + stackPane.getLayoutX(),
                ICON_SIZE / 2 + stackPane.getLayoutY()
        );

        endPosition = new Point2D(
                stackPane.getLayoutX() + stackPane.getPrefWidth() - ICON_SIZE / 2,
                ICON_SIZE / 2 + stackPane.getLayoutY()
        );
    }

    private Circle getIconForItem(PipelineItem<CarEngine> item) {

        Circle icon = icons.get(item);

        if (icon == null) {
            Circle newIcon = new Circle();
            newIcon.setLayoutX(beginPosition.getX());
            newIcon.setLayoutY(beginPosition.getY());
            newIcon.setRadius(10);
            newIcon.setStroke(Color.rgb(240, 0, 0));
            newIcon.setFill(Color.rgb(240, 128, 128));
            icons.put(item, newIcon);

            stackPane.getChildren().add(newIcon);
            return newIcon;
        }

        return icon;
    }

    private void removeIconForItem(PipelineItem<CarEngine> item) {
        var icon = icons.remove(item);
        if (icon != null) {
            stackPane.getChildren().remove(icon);
        }
    }

    public void initialize() {
        initializeCoordinates();
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void start() {
                super.start();
            }

            @Override
            public void handle(long timestamp) {

                long now = System.currentTimeMillis();
                pipeline.getItems().forEach(item -> {

                    var icon = getIconForItem(item);
                    float progress = item.currentProgress(now);

                    if (progress > 1.0) {
                        removeIconForItem(item);
                    } else {
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
