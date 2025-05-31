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
import lk.ijse.desktop.myfx.myfinalproject.Dto.RawMaterialPurchaseDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.RawMaterialPurchaseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RawMaterialPurchaseController implements Initializable {
    public AnchorPane getAncRawMaterialPurchase() {
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadSupplierId();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadSupplierId() throws SQLException {
        ArrayList<String> supplierIds = RawMaterialPurchaseModel.getAllSupplierId();
        ObservableList<String> observableList = FXCollections.observableArrayList(supplierIds);
        observableList.addAll(supplierIds);
        comSupplierId.setItems(observableList);
    }

    @FXML
    private AnchorPane ancRawMaterial;
    private String path;

    @FXML
    private TableColumn<RawMaterialPurchaseDto, String> colDate;

    @FXML
    private TableColumn<RawMaterialPurchaseDto, String> colMaterialName;

    @FXML
    private TableColumn<RawMaterialPurchaseDto, Double> colPrice;

    @FXML
    private TableColumn<RawMaterialPurchaseDto, String> colPurchaseId;

    @FXML
    private TableColumn<RawMaterialPurchaseDto, Integer> colQuantity;

    @FXML
    private TableColumn<RawMaterialPurchaseDto, String> colSupplierId;

    @FXML
    private TableView<RawMaterialPurchaseDto> tblRawMaterialPurchase;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtMaterialName;

    @FXML
    private TextField txtPrice;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private ComboBox<String> comSupplierId;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Raw Material Purchase");
        alert.setContentText("Are you sure you want to delete Raw Material Purchase?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDelete = new RawMaterialPurchaseModel().deleteRawMaterialPurchase(new RawMaterialPurchaseDto(id));
                if (isDelete) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Delete Saved").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Not Saved").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Delete Not Saved").show();
            }
        }
    }

    private void loadNextId() throws SQLException {
        RawMaterialPurchaseModel rawMaterialPurchaseModel = new RawMaterialPurchaseModel();
        String id = rawMaterialPurchaseModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int quantity=Integer.parseInt(txtQuantity.getText());
        double price=Double.parseDouble(txtPrice.getText());
        RawMaterialPurchaseDto rawMaterialPurchaseDto = new RawMaterialPurchaseDto(lblId.getText(),comSupplierId.getValue(),txtMaterialName.getText(),txtDate.getText(),quantity,price);

        try {
            RawMaterialPurchaseModel rawMaterialPurchaseModel = new RawMaterialPurchaseModel();
            boolean isSaved = rawMaterialPurchaseModel.saveRawMaterialPurchase(rawMaterialPurchaseDto);
            if(isSaved){
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Saved", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Save Failed", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Save Failed", ButtonType.OK).show();
        }
    }
    private void clearFields() throws SQLException {
        loadTable();
        lblId.setText("");
        comSupplierId.setValue("");
        txtMaterialName.setText("");
        txtDate.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
        });
    }
    private void loadTable() {
        colPurchaseId.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            RawMaterialPurchaseModel rawMaterialPurchaseModel = new RawMaterialPurchaseModel();
            ArrayList<RawMaterialPurchaseDto> rawMaterialPurchaseDtos = rawMaterialPurchaseModel.viewAllRawMaterialPurchase();
            if(rawMaterialPurchaseDtos != null){
                ObservableList<RawMaterialPurchaseDto> list = FXCollections.observableArrayList(rawMaterialPurchaseDtos);
                tblRawMaterialPurchase.setItems(list);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
        RawMaterialPurchaseDto rawMaterialPurchaseDto = new RawMaterialPurchaseDto(lblId.getText(), comSupplierId.getValue(), txtMaterialName.getText(), txtDate.getText(), quantity, price);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update Raw Material Purchase");
        alert.setContentText("Are you sure you want to update Raw Material Purchase?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isSave = RawMaterialPurchaseModel.updateRawMaterialPurchase(rawMaterialPurchaseDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Updated Successfully", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Update Failed", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Update Failed", ButtonType.OK).show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        RawMaterialPurchaseDto rawMaterialPurchaseDto = (RawMaterialPurchaseDto) tblRawMaterialPurchase.getSelectionModel().getSelectedItem();
        if(rawMaterialPurchaseDto!=null){
            lblId.setText(rawMaterialPurchaseDto.getPurchaseId());
            comSupplierId.setValue(rawMaterialPurchaseDto.getSupplierId());
            txtMaterialName.setText(rawMaterialPurchaseDto.getMaterialName());
            txtDate.setText(String.valueOf(rawMaterialPurchaseDto.getDate()));
            txtQuantity.setText(String.valueOf(rawMaterialPurchaseDto.getQuantity()));
            txtPrice.setText(String.valueOf(rawMaterialPurchaseDto.getUnitPrice()));
        }
    }

    public void btnGoToSupplierOnAction(ActionEvent actionEvent) {
        navigateTo("/View/SupplierView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancRawMaterial.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancRawMaterial.widthProperty());
            anchorPane.prefHeightProperty().bind(ancRawMaterial.heightProperty());
            ancRawMaterial.getChildren().add(anchorPane);
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

    public void comSupplierIdOnAction(ActionEvent actionEvent) {
        String selectedSupplierId = (String) comSupplierId.getSelectionModel().getSelectedItem();
        System.out.println(selectedSupplierId);
    }
}
