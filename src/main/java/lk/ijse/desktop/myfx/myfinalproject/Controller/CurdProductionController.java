package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.desktop.myfx.myfinalproject.Dto.CurdProductionDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.CurdProductionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CurdProductionController implements Initializable {
    public AnchorPane getAncCurdProduction(){
        return null;
    }


    @FXML
    private AnchorPane ancCurdProduction;
    private String path;

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
    private Label lblId;

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

    private final String quantityPattern = "^\\d+(\\.\\d+)?$";
    private final String potsSizePattern = "^\\d+(\\.\\d+)?$";
    private final String ingredientsPattern = "^[A-Za-z '-]+$";
    private final String storageIdPattern = "^\\d+$";

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
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

    private void loadNextId () throws SQLException {
        CurdProductionModel curdProductionModel = new CurdProductionModel();
        String nextId = curdProductionModel.getNextId();
        lblId.setText(nextId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(lblId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        int storageID = Integer.parseInt(txtStorageId.getText());
        CurdProductionDto curdProductionDto = new CurdProductionDto(id, txtProductionDate.getText(), txtExpiryDate.getText(), quantity, potsSize, txtIngredients.getText(), storageID);

        String quantityText = txtQuantity.getText();
        String pots = txtPotsSize.getText();
        String ingredients = txtIngredients.getText();
        String storageId = txtStorageId.getText();

        boolean isValidQuantity = quantityText.matches(quantityPattern);
        boolean isValidPots = pots.matches(potsSizePattern);
        boolean isValidIngredients = ingredients.matches(ingredientsPattern);
        boolean isValidStorageId = storageId.matches(storageIdPattern);

        if (isValidQuantity && isValidPots && isValidIngredients && isValidStorageId) {
            try {
                CurdProductionModel curdProductionModel = new CurdProductionModel();
                boolean isSave = CurdProductionModel.saveCurdProduction(curdProductionDto);
                if (isSave) {
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION, "Curd Production has been saved successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error while saving Curd Production").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error while saving Curd Production").show();
            }
        }
    }
    private void clearFields() {
        loadTable();
        txtExpiryDate.setText("");
        lblId.setText("");
        txtIngredients.setText("");
        txtPotsSize.setText("");
        txtProductionDate.setText("");
        txtQuantity.setText("");
        txtStorageId.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        int storageID = Integer.parseInt(txtStorageId.getText());
        CurdProductionDto curdProductionDto = new CurdProductionDto(id, txtProductionDate.getText(), txtExpiryDate.getText(), quantity, potsSize, txtIngredients.getText(), storageID);

        String quantityText = txtQuantity.getText();
        String pots = txtPotsSize.getText();
        String ingredients = txtIngredients.getText();
        String storageId = txtStorageId.getText();

        boolean isValidQuantity = quantityText.matches(quantityPattern);
        boolean isValidPots = pots.matches(potsSizePattern);
        boolean isValidIngredients = ingredients.matches(ingredientsPattern);
        boolean isValidStorageId = storageId.matches(storageIdPattern);

        if (isValidQuantity && isValidPots && isValidIngredients && isValidStorageId) {
            try {
                boolean isSave = CurdProductionModel.updateCurdProduction(curdProductionDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error while updating Curd Production").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error updating Curd Production").show();
            }
        }
    }
    public void tableOnClick(MouseEvent mouseEvent) {
        CurdProductionDto curdProductionDto = (CurdProductionDto) tblCurdProduction.getSelectionModel().getSelectedItem();
        if(curdProductionDto != null){
            lblId.setText(String.valueOf(curdProductionDto.getProductionId()));
            txtProductionDate.setText(String.valueOf(curdProductionDto.getProductionDate()));
            txtExpiryDate.setText(String.valueOf(curdProductionDto.getExpiryDate()));
            txtQuantity.setText(String.valueOf(curdProductionDto.getQuantity()));
            txtPotsSize.setText(String.valueOf(curdProductionDto.getPotsSize()));
            txtIngredients.setText(String.valueOf(curdProductionDto.getIngredients()));
            txtStorageId.setText(String.valueOf(curdProductionDto.getStorageId()));
        }
    }

    public void btnGoToCurdProduOnAction(ActionEvent actionEvent) {
        navigateTo("/View/CurdProductionView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancCurdProduction.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancCurdProduction.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCurdProduction.heightProperty());
            ancCurdProduction.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }

    public void btnGoToStockOnAction(ActionEvent actionEvent) {
        navigateTo("/View/StockView.fxml");
    }

    public void txtQuantityChange(KeyEvent keyEvent) {
        String qty = txtQuantity.getText();
        boolean isValid = qty.matches(quantityPattern);
        if (!isValid) {
            txtQuantity.setStyle(txtQuantity.getStyle()  + ";-fx-border-color: red");
        }else {
            txtQuantity.setStyle(txtQuantity.getStyle()  + ";-fx-border-color: blue");
        }
    }

    public void txtPotsSizeChange(KeyEvent keyEvent) {
        String potsSize = txtPotsSize.getText();
        boolean isValid = potsSize.matches(potsSizePattern);
        if (!isValid) {
            txtPotsSize.setStyle(txtPotsSize.getStyle()  + ";-fx-border-color: red");
        }else {
            txtPotsSize.setStyle(txtPotsSize.getStyle()  + ";-fx-border-color: blue");
        }
    }

    public void txtIngredientsChange(KeyEvent keyEvent) {
        String ingredients = txtIngredients.getText();
        boolean isValid = ingredients.matches(ingredientsPattern);
        if (!isValid) {
            txtIngredients.setStyle(txtIngredients.getStyle() + ";-fx-border-color: red");
        }else {
            txtIngredients.setStyle(txtIngredients.getStyle() + ";-fx-border-color: blue");
        }
    }

    public void txtStorageIdChange(KeyEvent keyEvent) {
        String storageId = txtStorageId.getText();
        boolean isValid = storageId.matches(storageIdPattern);
        if (!isValid) {
            txtStorageId.setStyle(txtStorageId.getStyle() + ";-fx-border-color: red");
        }else {
            txtStorageId.setStyle(txtStorageId.getStyle() + ";-fx-border-color: blue");
        }
    }
}
