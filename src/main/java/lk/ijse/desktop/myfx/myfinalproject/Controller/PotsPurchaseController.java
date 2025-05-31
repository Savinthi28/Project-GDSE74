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
import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsPurchaseDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.PotsPurchaseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PotsPurchaseController implements Initializable {
    public AnchorPane getAncPotsPurchase(){
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadPotsSize();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadPotsSize() throws SQLException {
        ArrayList<Integer> potsSize = PotsPurchaseModel.getAllPotsSize();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(potsSize);
        observableList.addAll(potsSize);
        comPotsSize.setItems(observableList);
    }


    @FXML
    private AnchorPane ancPotsPurchase;
    private String path;

    @FXML
    private TableColumn<PotsPurchaseDto, String> colDate;

    @FXML
    private TableColumn<PotsPurchaseDto, String> colId;

    @FXML
    private TableColumn<PotsPurchaseDto, Integer> colPotsSize;

    @FXML
    private TableColumn<PotsPurchaseDto, Double> colPrice;

    @FXML
    private TableColumn<PotsPurchaseDto, Integer> colQuantity;

    @FXML
    private TableView<PotsPurchaseDto> tblPotsPurchase;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblId;

    @FXML
    private ComboBox<Integer> comPotsSize;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Pots Purchase");
        alert.setContentText("Are you sure you want to delete pots purchase?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDelete = new PotsPurchaseModel().deletePotsPurchase(new PotsPurchaseDto(id));
                if (isDelete) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Pots Purchase Deleted Successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Pots Purchase Deletion Failed").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Pots Purchase Deletion Failed").show();
            }
        }
    }

    private void loadNextId () throws SQLException {
        PotsPurchaseModel potsPurchaseModel = new PotsPurchaseModel();
        String id = potsPurchaseModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        PotsPurchaseDto potsPurchaseDto = new PotsPurchaseDto(lblId.getText(),potsSize,txtDate.getText(),quantity,unitPrice);

        try {
            PotsPurchaseModel potsPurchaseModel = new PotsPurchaseModel();
            boolean isSave = potsPurchaseModel.savePotsPurchase(potsPurchaseDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Pots Purchase Successfully Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots Purchase Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots Purchase Failed").show();
        }
    }
    private void clearFields() throws SQLException {
        loadTable();
        lblId.setText("");
        comPotsSize.setValue(null);
        txtDate.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
        });
    }
    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        colPotsSize.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            PotsPurchaseModel potsPurchaseModel = new PotsPurchaseModel();
            ArrayList<PotsPurchaseDto> potsPurchaseDtos = potsPurchaseModel.viewAllPotsPurchase();
            if (potsPurchaseDtos != null) {
                ObservableList<PotsPurchaseDto> list = FXCollections.observableArrayList(potsPurchaseDtos);
                tblPotsPurchase.setItems(list);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        PotsPurchaseDto potsPurchaseDto = new PotsPurchaseDto(lblId.getText(), potsSize, txtDate.getText(), quantity, unitPrice);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update Pots Purchase");
        alert.setContentText("Are you sure you want to update pots purchase?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isSave = PotsPurchaseModel.updatePotsPurchase(potsPurchaseDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Pots Purchase Successfully Updated").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Pots Purchase Failed").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Pots Purchase Faile").show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        PotsPurchaseDto potsPurchaseDto = (PotsPurchaseDto) tblPotsPurchase.getSelectionModel().getSelectedItem();
        if (potsPurchaseDto != null) {
            lblId.setText(potsPurchaseDto.getPurchaseId());
            comPotsSize.setValue(Integer.valueOf(String.valueOf(potsPurchaseDto.getPotsSize())));
            txtDate.setText(potsPurchaseDto.getDate());
            txtQuantity.setText(String.valueOf(potsPurchaseDto.getQuantity()));
            txtUnitPrice.setText(String.valueOf(potsPurchaseDto.getPrice()));
        }
    }

    public void btnGoToSupplierOnAction(ActionEvent actionEvent) {
        navigateTo("/View/SupplierView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancPotsPurchase.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancPotsPurchase.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPotsPurchase.heightProperty());
            ancPotsPurchase.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }

    public void btnGoToPotsInventoryOnAction(ActionEvent actionEvent) {
        navigateTo("/View/PotsInventoryView.fxml");
    }

    public void btnGoToPotsPurchaseOnAction(ActionEvent actionEvent) {
        navigateTo("/View/PotsPurchaseView.fxml");
    }

    public void btnGoToRawMaterialOnAction(ActionEvent actionEvent) {
        navigateTo("/View/RawMaterialPurchaseView.fxml");
    }

    public void comPotsSizeOnAction(ActionEvent actionEvent) {
        Integer selectedPotsSize = (Integer) comPotsSize.getSelectionModel().getSelectedItem();
        System.out.println(selectedPotsSize);
    }
}
