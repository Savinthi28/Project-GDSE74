package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkStorageDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.MilkStorageModel;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MilkStorageController implements Initializable {

    public AnchorPane getAncMilkStorage() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadMilkStorage();
            clearField();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadMilkStorage() throws SQLException {
        ArrayList<String> milkStorage = MilkStorageModel.getAllMilkStorage();
        ObservableList<String> observableList = FXCollections.observableArrayList(milkStorage);
        observableList.addAll(milkStorage);
        comMilkStorage.setItems(observableList);
    }


    @FXML
    private AnchorPane ancMilkStorage;
    private String path;
    @FXML
    private TableColumn<MilkStorageDto, String> colCollectionId;

    @FXML
    private TableColumn<MilkStorageDto, String> colDate;

    @FXML
    private TableColumn<MilkStorageDto, Time> colDuration;

    @FXML
    private TableColumn<MilkStorageDto, String> colStorageId;

    @FXML
    private TableColumn<MilkStorageDto, Double> colTemperature;

    @FXML
    private TableView<MilkStorageDto> tblMilkStorage;

    @FXML
    private ComboBox<String> comMilkStorage;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDuration;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtTemperature;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearField();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();
        Time duration = Time.valueOf(txtDuration.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Milk Storage");
        alert.setContentText("Are you sure you want to delete milk storage?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDelete = new MilkStorageModel().deleteMilkStorage(new MilkStorageDto(id, comMilkStorage.getValue(), txtDate.getText(), duration, temperature));
                if (isDelete) {
                    clearField();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Milk Storage Deleted Successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Milk Storage Deletion Failed").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Milk Storage Deletion Failed").show();
            }
        }
    }


    private void loadNextId() throws SQLException {
        MilkStorageModel milkStorageModel = new MilkStorageModel();
        String id  = milkStorageModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Time duration = Time.valueOf(txtDuration.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());
        MilkStorageDto milkStorageDto = new MilkStorageDto(lblId.getText(),comMilkStorage.getValue(),txtDate.getText(),duration,temperature);

        try {
            MilkStorageModel milkStorageModel = new MilkStorageModel();
            boolean isSave = milkStorageModel.saveMilkStorage(milkStorageDto);
            if (isSave) {
                clearField();
                new Alert(Alert.AlertType.INFORMATION, "Milk Storage has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Milk Storage has not been saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Milk Storage has not been saved").show();
        }
    }
private void clearField() throws SQLException {
        loadTable();
        lblId.setText("");
        comMilkStorage.setValue("");
        txtDate.setText("");
        txtDuration.setText("");
        txtTemperature.setText("");

    loadNextId();
    Platform.runLater(()-> {
        lblId.setText(lblId.getText());
    });
}
private void loadTable() {
        colStorageId.setCellValueFactory(new PropertyValueFactory<>("storageId"));
        colCollectionId.setCellValueFactory(new PropertyValueFactory<>("collectionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        try {
            MilkStorageModel milkStorageModel = new MilkStorageModel();
            ArrayList<MilkStorageDto> milkStorageDtos = milkStorageModel.viewAllMilkStorage();
            if (milkStorageDtos != null) {
                ObservableList<MilkStorageDto> list = FXCollections.observableArrayList(milkStorageDtos);
                tblMilkStorage.setItems(list);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
}
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Time duration = Time.valueOf(txtDuration.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());
        MilkStorageDto milkStorageDto = new MilkStorageDto(lblId.getText(), comMilkStorage.getValue(), txtDate.getText(), duration, temperature);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update Milk Storage");
        alert.setContentText("Are you sure you want to update milk storage?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isSave = MilkStorageModel.updateMilkStorage(milkStorageDto);
                if (isSave) {
                    clearField();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Milk Storage has been updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Milk Storage has not been updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Milk Storage has not been updated").show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        MilkStorageDto milkStorageDto = (MilkStorageDto) tblMilkStorage.getSelectionModel().getSelectedItem();
        if (milkStorageDto != null) {
            lblId.setText(milkStorageDto.getStorageId());
            comMilkStorage.setValue(milkStorageDto.getCollectionId());
            txtDate.setText(String.valueOf(milkStorageDto.getDate()));
            txtDuration.setText(String.valueOf(milkStorageDto.getDuration()));
            txtTemperature.setText(String.valueOf(milkStorageDto.getTemperature()));
        }
    }

    public void btnGoToMilkCollectionOnAction(ActionEvent actionEvent) {
        navigateTo("/View/MilkCollectionView.fxml");
    }
    private <Sring> void navigateTo(Sring path){
        try {
            ancMilkStorage.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancMilkStorage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMilkStorage.heightProperty());
            ancMilkStorage.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }
    public void btnGoToMilkStorageOnAction(ActionEvent actionEvent) {
        navigateTo("/View/MilkStorageView.fxml");
    }

    public void btnGoToQualityCheckOnAction(ActionEvent actionEvent) {
        navigateTo("/View/QualityCheckView.fxml");
    }

    public void comMilkStorageOnAction(ActionEvent actionEvent) {
        String selectedMilkStorage = (String)comMilkStorage.getSelectionModel().getSelectedItem();
        System.out.println(selectedMilkStorage);
    }
}
