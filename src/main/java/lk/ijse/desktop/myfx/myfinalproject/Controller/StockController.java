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
import lk.ijse.desktop.myfx.myfinalproject.Dto.StockDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.StockModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    public AnchorPane getAncStock(){
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private AnchorPane ancStock;
    private String path;

    @FXML
    private TableColumn<StockDto, String> colDate;

    @FXML
    private TableColumn<StockDto, Integer> colProductionId;

    @FXML
    private TableColumn<StockDto, Integer> colQuantity;

    @FXML
    private TableColumn<StockDto, Integer> colStockId;

    @FXML
    private TableColumn<StockDto, String> colStockType;

    @FXML
    private TableView<StockDto> tblStock;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtProdctionId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtStockId;

    @FXML
    private TextField txtStockType;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtStockId.getText());
        try {
            boolean isDelete = new StockModel().deleteStock(new StockDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION,"Stock Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Stock Not Deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Stock Not Deleted").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int stockId = Integer.parseInt(txtStockId.getText());
        int productionId = Integer.parseInt(txtProdctionId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        StockDto stockDto = new StockDto(stockId,productionId,txtDate.getText(),quantity,txtStockType.getText());

        try {
            StockModel stockModel = new StockModel();
            boolean isSaved = stockModel.saveStock(stockDto);
            if (isSaved) {
                clearFields();
               new Alert(Alert.AlertType.INFORMATION, "Stock added successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    private void clearFields(){
        loadTable();
        txtStockId.setText("");
        txtProdctionId.setText("");
        txtDate.setText("");
        txtQuantity.setText("");
        txtStockType.setText("");
    }
    private void loadTable() {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colProductionId.setCellValueFactory(new PropertyValueFactory<>("productionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStockType.setCellValueFactory(new PropertyValueFactory<>("stockType"));

        try {
            StockModel stockModel = new StockModel();
            ArrayList<StockDto> stockDtos = stockModel.viewAllStock();
            if (stockDtos != null) {
                ObservableList<StockDto> observableList = FXCollections.observableArrayList(stockDtos);
                tblStock.setItems(observableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int stockId = Integer.parseInt(txtStockId.getText());
        int productionId = Integer.parseInt(txtProdctionId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        StockDto stockDto = new StockDto(stockId,productionId,txtDate.getText(),quantity,txtStockType.getText());
        try {
            boolean isSave = StockModel.updateSrock(stockDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION,"Stock updated successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        StockDto stockDto = (StockDto) tblStock.getSelectionModel().getSelectedItem();
        if (stockDto != null) {
            txtStockId.setText(String.valueOf(stockDto.getStockId()));
            txtProdctionId.setText(String.valueOf(stockDto.getProductionId()));
            txtDate.setText(stockDto.getDate());
            txtQuantity.setText(String.valueOf(stockDto.getQuantity()));
            txtStockType.setText(stockDto.getStockType());
        }
    }

    public void btnGoToCurdProduOnAction(ActionEvent actionEvent) {
        navigateTo("/View/CurdProductionView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancStock.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancStock.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStock.heightProperty());
            ancStock.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }

    public void btnGoToStockOnAction(ActionEvent actionEvent) {
        navigateTo("/View/StockView.fxml");
    }
}
