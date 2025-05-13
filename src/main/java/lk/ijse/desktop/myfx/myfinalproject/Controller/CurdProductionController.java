package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.CurdProductionDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.CurdProductionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CurdProductionController implements Initializable {

    @FXML
    private TableColumn<CurdProductionDto, String> colExpiryDate;

    @FXML
    private TableColumn<CurdProductionDto, Integer> colId;

    @FXML
    private TableColumn<CurdProductionDto, String> colIngredients;

    @FXML
    private TableColumn<CurdProductionDto, Integer> colPotsSize;

    @FXML
    private TableColumn<CurdProductionDto, String> colProductionDate;

    @FXML
    private TableColumn<CurdProductionDto, Integer> colQuantity;

    @FXML
    private TableColumn<CurdProductionDto, Integer> colStorageID;

    @FXML
    private TableView<CurdProductionDto> tblCurdProduction;

    @FXML
    private TextField txtExpiryDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtIngredients;

    @FXML
    private TextField txtPotsSize;

    @FXML
    private TextField txtProductionDate;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtStorageId;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        try {
            boolean isDelete = new CurdProductionModel().deleteCurdProduction(new CurdProductionDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Not Deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Not Deleted").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(txtId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        int storageID = Integer.parseInt(txtStorageId.getText());
        CurdProductionDto curdProductionDto = new CurdProductionDto(id,txtProductionDate.getText(),txtExpiryDate.getText(),quantity,potsSize,txtIngredients.getText(),storageID);

        try {
            CurdProductionModel curdProductionModel = new CurdProductionModel();
            boolean isSave = CurdProductionModel.saveCurdProduction(curdProductionDto);
            if(isSave){
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Curd Production has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error while saving Curd Production").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error while saving Curd Production").show();
        }
    }
    private void clearFields() {
        loadTable();
        txtExpiryDate.setText("");
        txtId.setText("");
        txtIngredients.setText("");
        txtPotsSize.setText("");
        txtProductionDate.setText("");
        txtQuantity.setText("");
        txtStorageId.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("productionId"));
        colProductionDate.setCellValueFactory(new PropertyValueFactory<>("productionDate"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPotsSize.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        colStorageID.setCellValueFactory(new PropertyValueFactory<>("storageId"));

        try {
            CurdProductionModel curdProductionModel = new CurdProductionModel();
            ArrayList<CurdProductionDto> curdProductionDtos = curdProductionModel.viewAllCurdProduction();
            if(curdProductionDtos != null){
                ObservableList<CurdProductionDto> observableList = FXCollections.observableArrayList(curdProductionDtos);
                tblCurdProduction.setItems(observableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void tableOnClick(MouseEvent mouseEvent) {
        CurdProductionDto curdProductionDto = (CurdProductionDto) tblCurdProduction.getSelectionModel().getSelectedItem();
        if(curdProductionDto != null){
            txtId.setText(String.valueOf(curdProductionDto.getProductionId()));
            txtProductionDate.setText(String.valueOf(curdProductionDto.getProductionDate()));
            txtExpiryDate.setText(String.valueOf(curdProductionDto.getExpiryDate()));
            txtQuantity.setText(String.valueOf(curdProductionDto.getQuantity()));
            txtPotsSize.setText(String.valueOf(curdProductionDto.getPotsSize()));
            txtIngredients.setText(String.valueOf(curdProductionDto.getIngredients()));
            txtStorageId.setText(String.valueOf(curdProductionDto.getStorageId()));
        }
    }
}
