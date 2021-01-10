package org.monjasa.carfactory.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.domain.CarAccessory;
import org.monjasa.carfactory.domain.CarBody;
import org.monjasa.carfactory.domain.CarEngine;
import org.monjasa.carfactory.model.CarComponentProducersModel;
import org.monjasa.carfactory.model.CarComponentStorage;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class MainSceneController {

    private final CarComponentProducersModel carComponentProducersModel;

    @FXML private Label carEngineStorageState;
    @FXML private Label carEngineStorageProduced;
    @FXML private Slider carEngineProducingRateSlider;

    @FXML private Label carBodyStorageState;
    @FXML private Label carBodyStorageProduced;
    @FXML private Slider carBodyProducingRateSlider;

    @FXML private Label carAccessoryStorageState;
    @FXML private Label carAccessoryStorageProduced;
    @FXML private Slider carAccessoryProducingRateSlider;

    public void initialize() {

        CarComponentStorage<CarEngine> carEngineStorage = carComponentProducersModel.getCarEngineStorage();

        carEngineStorageState.textProperty().bind(
                Bindings.size(carEngineStorage.getObservableBlockingQueue()).asString()
                        .concat(" / ")
                        .concat(carEngineStorage.getStorageCapacity().asString())
        );

        carEngineStorageProduced.textProperty().bind(
                new SimpleStringProperty("Total: ")
                        .concat(carEngineStorage.getProducedCarComponentsCount().asString())
        );

        carComponentProducersModel.getCarComponentProducers().stream()
                .filter(carComponentProducer -> carComponentProducer.getCarComponentStorage() == carEngineStorage)
                .forEach(carComponentProducer -> carComponentProducer.getProducingRate().bind(carEngineProducingRateSlider.valueProperty()));

        CarComponentStorage<CarBody> carBodyStorage = carComponentProducersModel.getCarBodyStorage();

        carBodyStorageState.textProperty().bind(
                Bindings.size(carBodyStorage.getObservableBlockingQueue()).asString()
                        .concat(" / ")
                        .concat(carBodyStorage.getStorageCapacity().asString())
        );

        carBodyStorageProduced.textProperty().bind(
                new SimpleStringProperty("Total: ")
                        .concat(carBodyStorage.getProducedCarComponentsCount().asString())
        );

        carComponentProducersModel.getCarComponentProducers().stream()
                .filter(carComponentProducer -> carComponentProducer.getCarComponentStorage() == carBodyStorage)
                .forEach(carComponentProducer -> carComponentProducer.getProducingRate().bind(carBodyProducingRateSlider.valueProperty()));

        CarComponentStorage<CarAccessory> carAccessoryStorage = carComponentProducersModel.getCarAccessoryStorage();

        carAccessoryStorageState.textProperty().bind(
                Bindings.size(carAccessoryStorage.getObservableBlockingQueue()).asString()
                        .concat(" / ")
                        .concat(carAccessoryStorage.getStorageCapacity().asString())
        );

        carAccessoryStorageProduced.textProperty().bind(
                new SimpleStringProperty("Total: ")
                        .concat(carAccessoryStorage.getProducedCarComponentsCount().asString())
        );

        carComponentProducersModel.getCarComponentProducers().stream()
                .filter(carComponentProducer -> carComponentProducer.getCarComponentStorage() == carAccessoryStorage)
                .forEach(carComponentProducer -> carComponentProducer.getProducingRate().bind(carAccessoryProducingRateSlider.valueProperty()));
    }
}