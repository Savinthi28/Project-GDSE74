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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    public AnchorPane getAncStock(){
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadProductionId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadProductionId()throws SQLException {
        ArrayList<Integer> productionIds = StockModel.getAllProductionId();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(productionIds);
        observableList.addAll(productionIds);
        comProductionId.setItems(observableList);
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
    private ComboBox<Integer> comProductionId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtStockType;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
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

    private void loadNextId() throws SQLException {
        StockModel stockModel = new StockModel();
        String id = stockModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int stockId = Integer.parseInt(lblId.getText());
        int productionId = Integer.parseInt(String.valueOf(comProductionId.getValue()));
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
        lblId.setText("");
        comProductionId.setValue(Integer.valueOf(""));
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
        int stockId = Integer.parseInt(lblId.getText());
        int productionId = Integer.parseInt(String.valueOf(comProductionId.getValue()));
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
            lblId.setText(String.valueOf(stockDto.getStockId()));
            comProductionId.setValue(Integer.valueOf(String.valueOf(stockDto.getProductionId())));
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

    public void comProductionIdOnAction(ActionEvent actionEvent) {
        Integer selectedProductionId = (Integer) comProductionId.getSelectionModel().getSelectedItem();
        System.out.println(selectedProductionId);
    }
}
