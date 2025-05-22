package lk.ijse.desktop.myfx.myfinalproject.Controller;

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
import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkCollectionDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.MilkCollectionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MilkCollectionController implements Initializable {

    public AnchorPane getAncMilkCollection() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadMilkCollectionBuffaloId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadMilkCollectionBuffaloId() throws SQLException {
        ArrayList<String> milkCollectionBuffaloIds = MilkCollectionModel.getAllMilkCollectionBuffaloId();
        ObservableList<String> observableList = FXCollections.observableArrayList(milkCollectionBuffaloIds);
        observableList.addAll(milkCollectionBuffaloIds);
        comMilkCollection.setItems(observableList);
    }

    @FXML
    private AnchorPane ancMilkCollection;
    private String path;

    @FXML
    private TableColumn<MilkCollectionDto, String> colBuffaloId;

    @FXML
    private TableColumn<MilkCollectionDto, String> colDate;

    @FXML
    private TableColumn<MilkCollectionDto, Integer> colId;

    @FXML
    private TableColumn<MilkCollectionDto, Double> colQuantity;

    @FXML
    private TableView<MilkCollectionDto> tblMilkCollection;

    @FXML
    private ComboBox<String> comMilkCollection;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtQuantity;
    @FXML
    public void btnGoToMilkCollectionOnAction(ActionEvent actionEvent) {
        navigateTo("/View/MilkCollectionView.fxml");
    }

        private <Sring> void navigateTo(Sring path){
        try {
            ancMilkCollection.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancMilkCollection.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMilkCollection.heightProperty());
            ancMilkCollection.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        try {
            boolean isDelete = new MilkCollectionModel().deleteMikCollection(new MilkCollectionDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "MikCollection deleted successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "MikCollection deleted Faield").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "MikCollection deleted Faield").show();
        }
    }

    private void loadNextId () throws SQLException {
        MilkCollectionModel milkCollectionModel = new MilkCollectionModel();
        String nextId = milkCollectionModel.getNextId();
        lblId.setText(nextId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        double quantity = Double.parseDouble(txtQuantity.getText());
        MilkCollectionDto milkCollectionDto = new MilkCollectionDto(id, txtDate.getText(), quantity, comMilkCollection.getValue());

        try {
            MilkCollectionModel milkCollectionModel = new MilkCollectionModel();
            boolean isSave = milkCollectionModel.saveMilkCollection(milkCollectionDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Milk Collection has been saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Milk Collection has not been saved").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Milk Collection has not been saved");
        }
    }

    private void clearFields() {
        loadTable();
        lblId.setText("");
        txtDate.setText("");
        txtQuantity.setText("");
        comMilkCollection.setValue("");
    }

    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colBuffaloId.setCellValueFactory(new PropertyValueFactory<>("buffaloId"));

        try {
            MilkCollectionModel milkCollectionModel = new MilkCollectionModel();
            ArrayList<MilkCollectionDto> milkCollectionDtos = milkCollectionModel.viewAllMilkCollection();
            if (milkCollectionDtos != null) {
                ObservableList<MilkCollectionDto> observableList = FXCollections.observableArrayList(milkCollectionDtos);
                tblMilkCollection.setItems(observableList);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        double quantity = Double.parseDouble(txtQuantity.getText());
        MilkCollectionDto milkCollectionDto = new MilkCollectionDto(id, txtDate.getText(), quantity, comMilkCollection.getValue());
        try {
            boolean isSave = MilkCollectionModel.updateMilkCollection(milkCollectionDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Milk Collection has been updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Milk Collection has not been updated").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Milk Collection has not been update");
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        MilkCollectionDto milkCollectionDto = (MilkCollectionDto) tblMilkCollection.getSelectionModel().getSelectedItem();
        if (milkCollectionDto != null) {
            lblId.setText(String.valueOf(milkCollectionDto.getId()));
            txtDate.setText(milkCollectionDto.getDate());
            txtQuantity.setText(String.valueOf(milkCollectionDto.getQuantity()));
            comMilkCollection.setValue(milkCollectionDto.getBuffaloId());
        }
    }

    public void btnGoToMilkStorageOnAction(ActionEvent actionEvent) {
        navigateTo("/View/MilkStorageView.fxml");
    }

    public void btnGoToQualityCheckOnAction(ActionEvent actionEvent) {
        navigateTo("/View/QualityCheckView.fxml");
    }

    public void comMilkCollectionOnAction(ActionEvent actionEvent) {
        String selectedMilkCollection = (String) comMilkCollection.getSelectionModel().getSelectedItem();
        System.out.println(selectedMilkCollection);
    }
}

