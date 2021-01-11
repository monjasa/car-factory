package org.monjasa.carfactory.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.domain.Car;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.model.*;
import org.monjasa.carfactory.model.warehouse.AuditableWarehouse;
import org.monjasa.carfactory.model.warehouse.ProductWarehouse;
import org.monjasa.carfactory.service.facility.AbstractFacilityFactory;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class MainSceneController {

    private final CarComponentProducersModel carComponentProducersModel;
    private final CarDealersModel carDealersModel;
    private final CarPlantModel carPlantModel;

    @FXML private Label carEngineWarehouseState;
    @FXML private Label carEngineWarehouseTotal;
    @FXML private Slider carEngineProducingRate;

    @FXML private Label carBodyWarehouseState;
    @FXML private Label carBodyWarehouseTotal;
    @FXML private Slider carBodyProducingRate;

    @FXML private Label carAccessoryWarehouseState;
    @FXML private Label carAccessoryWarehouseTotal;
    @FXML private Slider carAccessoryProducingRate;

    @FXML private Label carWarehouseState;
    @FXML private Label carWarehouseTotal;

    @FXML private Label waitingCarCreationTasksCount;

    @FXML private Slider carRequestingRate;

    private void setupProducingInfo(
            AbstractFacilityFactory<? extends CarComponent> facilityFactory,
            Label warehouseState,
            Label warehouseTotal,
            Slider slider
    ) {

        warehouseState.textProperty().bind(
                facilityFactory.getProductWarehouse().sizeBinding().asString()
                        .concat(" / ")
                        .concat(facilityFactory.getProductWarehouse().capacityProperty().asString())
        );

        AuditableWarehouse auditableWarehouse = (AuditableWarehouse) facilityFactory.getProductWarehouse();
        warehouseTotal.textProperty().bind(
                new SimpleStringProperty("Total: ")
                        .concat(auditableWarehouse.auditSizeBinding().asString())
        );

        facilityFactory.getCarComponentProducers()
                .forEach(carComponentProducer -> carComponentProducer.producingRateProperty().bind(slider.valueProperty()));
    }

    public void initialize() {

        setupProducingInfo(
                carComponentProducersModel.getCarEngineFacilityFactory(),
                carEngineWarehouseState,
                carEngineWarehouseTotal,
                carEngineProducingRate
        );

        setupProducingInfo(
                carComponentProducersModel.getCarBodyFacilityFactory(),
                carBodyWarehouseState,
                carBodyWarehouseTotal,
                carBodyProducingRate
        );

        setupProducingInfo(
                carComponentProducersModel.getCarAccessoryFacilityFactory(),
                carAccessoryWarehouseState,
                carAccessoryWarehouseTotal,
                carAccessoryProducingRate
        );

        ProductWarehouse<Car> carWarehouse = carPlantModel.getCarWarehouse();
        carWarehouseState.textProperty().bind(
                carWarehouse.sizeBinding().asString()
                        .concat(" / ")
                        .concat(carWarehouse.capacityProperty().asString())
        );

        carWarehouseTotal.textProperty().bind(
                new SimpleStringProperty("Total: ")
                        .concat(((AuditableWarehouse) carWarehouse).auditSizeBinding().asString())
        );

        waitingCarCreationTasksCount.textProperty().bind(
                new SimpleStringProperty("Waiting: +")
                        .concat(carPlantModel.getWaitingTaskCount().asString())
        );

        carDealersModel.getCarDealers().forEach(carDealer -> carDealer.requestingRateProperty().bind(carRequestingRate.valueProperty().multiply(1000)));
    }
}