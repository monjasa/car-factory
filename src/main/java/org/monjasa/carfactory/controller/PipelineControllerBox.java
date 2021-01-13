package org.monjasa.carfactory.controller;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.domain.CarAccessory;
import org.monjasa.carfactory.domain.CarBody;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.model.transport.Pipeline;
import org.monjasa.carfactory.model.transport.PipelineItem;


import java.util.HashMap;
import java.util.Map;

public class PipelineControllerBox extends AnchorPane {

    private static final Map<Class<?>, Image> iconImages;

    static {
        iconImages = Map.of(
                CarEngine.class, new Image("/assets/images/car-engine-icon.png"),
                CarBody.class, new Image("/assets/images/car-body-icon.png"),
                CarAccessory.class, new Image("/assets/images/car-accessory-icon.png"),
                Car.class, new Image("/assets/images/car-product-icon.png")
        );
    }

    private final IntegerProperty iconSize = new SimpleIntegerProperty();

    private Pipeline pipeline;

    private final Map<PipelineItem, ImageView> icons = new HashMap<>();

    private Point2D beginPosition;
    private Point2D difference;

    private double yPosition;

    private void initializeCoordinates() {
        beginPosition = new Point2D(
                iconSize.get() / 2.0 + getLayoutX(),
                yPosition
        );

        Point2D endPosition = new Point2D(
                super.getLayoutX() + getWidth() - iconSize.get() / 2.0,
                yPosition
        );

        difference = endPosition.subtract(beginPosition);
    }

    private ImageView getIconForItem(PipelineItem item) {

        ImageView icon = icons.get(item);

        if (icon == null) {
            ImageView newIcon = new ImageView();
            newIcon.setImage(iconImages.get(item.getProduct().getClass()));
            newIcon.setFitHeight(iconSize.get());
            newIcon.setPreserveRatio(true);
            newIcon.setSmooth(true);
            newIcon.setCache(true);
            icons.put(item, newIcon);

            super.getChildren().add(newIcon);
            return newIcon;
        }

        return icon;
    }

    private void removeIconForItem(PipelineItem item) {
        ImageView imageView = icons.remove(item);
        if (imageView != null) {
            super.getChildren().remove(imageView);
        }
    }

    private void start() {

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void start() {
                super.start();
            }

            @Override
            public void handle(long timestamp) {
                initializeCoordinates();
                long now = System.currentTimeMillis();

                int i = 0;
                for (PipelineItem item : pipeline.getItems()) {

                    var icon = getIconForItem(item);
                    float progress = item.currentProgress(now);

                    if (progress > 1.0) {
                        removeIconForItem(item);
                    } else {

                        Point2D currentPosition = beginPosition.add(difference.multiply(progress));
                        i++;
                        icon.setLayoutX(currentPosition.getX() - iconSize.get() / 2.0);
                        icon.setLayoutY(yPosition + i * 25);
                    }
                }
            }
        };

        timer.start();
    }

    public IntegerProperty iconSizeProperty() {
        return iconSize;
    }

    public int getIconSize() {
        return iconSize.get();
    }

    public void setIconSize(int iconSize) {
        yPosition = iconSize / 2.0 + getLayoutY();
        this.iconSize.set(iconSize);
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
        start();
    }
}
