package org.monjasa.carfactory.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.monjasa.carfactory.domain.CarComponent;
import org.monjasa.carfactory.model.CarComponentProducersModel;
import org.monjasa.carfactory.model.warehouse.AuditableWarehouse;
import org.monjasa.carfactory.service.facility.AbstractFacilityFactory;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class CarComponentProducersController {

    private final CarComponentProducersModel carComponentProducersModel;

    @FXML private Label carEngineWarehouseState;
    @FXML private Label carEngineWarehouseTotal;
    @FXML private Slider carEngineProducingRate;

    @FXML private Label carBodyWarehouseState;
    @FXML private Label carBodyWarehouseTotal;
    @FXML private Slider carBodyProducingRate;

    @FXML private Label carAccessoryWarehouseState;
    @FXML private Label carAccessoryWarehouseTotal;
    @FXML private Slider carAccessoryProducingRate;

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
    }
}
