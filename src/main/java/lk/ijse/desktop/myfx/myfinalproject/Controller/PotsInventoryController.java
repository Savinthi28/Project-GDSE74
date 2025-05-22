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
import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsInventoryDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.PotsInventoryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PotsInventoryController implements Initializable {
    public AnchorPane getAncPotsInventory(){
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadPotsSize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadPotsSize() throws SQLException {
        ArrayList<Integer> potsSize = PotsInventoryModel.getAllPotsSize();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(potsSize);
        observableList.addAll(potsSize);
        comPotsSize.setItems(observableList);
    }

    @FXML
    private AnchorPane ancPotsInventory;
    private String path;

    @FXML
    private TableColumn<PotsInventoryDto, String> colCondition;

    @FXML
    private TableColumn<PotsInventoryDto, Integer> colId;

    @FXML
    private TableColumn<PotsInventoryDto, Integer> colPotsSize;

    @FXML
    private TableColumn<PotsInventoryDto, Integer> colQuantity;

    @FXML
    private TableView<PotsInventoryDto> tblPotsInventory;

    @FXML
    private TextField txtCondition;

    @FXML
    private Label lblId;

    @FXML
    private ComboBox<Integer> comPotsSize;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        try {
            boolean isDelete = new PotsInventoryModel().deletePotsInventory(new PotsInventoryDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Pots deleted successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots not deleted successfully").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots not deleted successfully").show();
        }
    }

    private void loadNextId() throws SQLException {
        PotsInventoryModel potsInventoryModel = new PotsInventoryModel();
        String id = potsInventoryModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
        PotsInventoryDto potsInventoryDto = new PotsInventoryDto(id,quantity,potsSize,txtCondition.getText());

        try {
            PotsInventoryModel potsInventoryModel = new PotsInventoryModel();
            boolean isSave = potsInventoryModel.savePotsInventory(potsInventoryDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Pots Inventory Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots Inventory Not Saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots Inventory Not Saved").show();
        }
    }
    private void clearFields() {
        loadTable();
        lblId.setText("");
        txtQuantity.setText("");
        comPotsSize.setValue(Integer.valueOf(""));
        txtCondition.setText("");
    }
    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPotsSize.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));

        try {
            PotsInventoryModel potsInventoryModel = new PotsInventoryModel();
            ArrayList<PotsInventoryDto> potsInventoryDtos = potsInventoryModel.viewAllPotsInventory();
            if (potsInventoryDtos != null) {
                ObservableList<PotsInventoryDto> observableList = FXCollections.observableArrayList(potsInventoryDtos);
                tblPotsInventory.setItems(observableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
        PotsInventoryDto potsInventoryDto = new PotsInventoryDto(id,quantity,potsSize,txtCondition.getText());
        try {
            boolean isSave = PotsInventoryModel.updatePotsInventory(potsInventoryDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Pots updated successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots not updated").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots not update").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        PotsInventoryDto potsInventoryDto = (PotsInventoryDto) tblPotsInventory.getSelectionModel().getSelectedItem();
        if (potsInventoryDto != null) {
            lblId.setText(String.valueOf(potsInventoryDto.getId()));
            txtQuantity.setText(String.valueOf(potsInventoryDto.getQuantity()));
            comPotsSize.setValue(Integer.valueOf(String.valueOf(potsInventoryDto.getPotsSize())));
            txtCondition.setText(String.valueOf(potsInventoryDto.getCondition()));
        }
    }

    public void btnGoToSupplierOnAction(ActionEvent actionEvent) {
        navigateTo("/View/SupplierView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancPotsInventory.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancPotsInventory.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPotsInventory.heightProperty());
            ancPotsInventory.getChildren().add(anchorPane);
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
