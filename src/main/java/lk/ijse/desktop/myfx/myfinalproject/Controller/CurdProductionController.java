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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.desktop.myfx.myfinalproject.Dto.CurdProductionDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.CurdProductionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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
    private TableColumn<CurdProductionDto, String> colId;

    @FXML
    private TableColumn<CurdProductionDto, String> colIngredients;

    @FXML
    private TableColumn<CurdProductionDto, Integer> colPotsSize;

    @FXML
    private TableColumn<CurdProductionDto, String> colProductionDate;

    @FXML
    private TableColumn<CurdProductionDto, Integer> colQuantity;

    @FXML
    private TableColumn<CurdProductionDto, String> colStorageID;

    @FXML
    private TableView<CurdProductionDto> tblCurdProduction;

    @FXML
    private TextField txtExpiryDate;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtIngredients;

    @FXML
    private ComboBox<Integer> comPotsSize;

    @FXML
    private ComboBox<String> comStorageId;

    @FXML
    private TextField txtProductionDate;

    @FXML
    private TextField txtQuantity;

    private final String quantityPattern = "^\\d+(\\.\\d+)?$";
    private final String ingredientsPattern = "^[A-Za-z '-]+$";

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String id = txtProductionDate.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete production");
        alert.setContentText("Are you sure you want to delete this production?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDelete = new CurdProductionModel().deleteCurdProduction(new CurdProductionDto(lblId.getText(), txtProductionDate.getText(), txtExpiryDate.getText(), quantity, potsSize, txtIngredients.getText(), id));
                if (isDelete) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Not Deleted").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Not Deleted").show();
            }
        }
    }
    private void loadNextId () throws SQLException {
        CurdProductionModel curdProductionModel = new CurdProductionModel();
        String nextId = curdProductionModel.getNextId();
        lblId.setText(nextId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
        CurdProductionDto curdProductionDto = new CurdProductionDto(lblId.getText(), txtProductionDate.getText(), txtExpiryDate.getText(), quantity, potsSize, txtIngredients.getText(),comStorageId.getValue());

        String quantityText = txtQuantity.getText();
        String ingredients = txtIngredients.getText();

        boolean isValidQuantity = quantityText.matches(quantityPattern);
        boolean isValidIngredients = ingredients.matches(ingredientsPattern);

        if (isValidQuantity && isValidIngredients) {
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
                new Alert(Alert.AlertType.ERROR, "Curd Production has not been save").show();
            }
        }
    }
    private void clearFields() throws SQLException {
        loadTable();
        txtExpiryDate.setText("");
        lblId.setText("");
        txtIngredients.setText("");
        comPotsSize.setValue(0);
        txtProductionDate.setText("");
        txtQuantity.setText("");
        comStorageId.setValue("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadPotsSize();
            loadStorageId();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadStorageId() throws SQLException {
        ArrayList<String> storageList = CurdProductionModel.getAllStorageId();
        ObservableList<String> observableList = FXCollections.observableArrayList(storageList);
        observableList.addAll(storageList);
        comStorageId.setItems(observableList);
    }

    private void loadPotsSize() throws SQLException {
        ArrayList<Integer> potsSizeList = CurdProductionModel.getAllPotsSize();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(potsSizeList);
        observableList.addAll(potsSizeList);
        comPotsSize.setItems(observableList);
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update production");
        alert.setContentText("Are you sure you want to update this production?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (txtQuantity.getText().isEmpty() || comPotsSize.getValue() == null) {
                    new Alert(Alert.AlertType.ERROR, "Quantity and Pot Size must be filled!").show();
                    return;
                }
                int quantity;
                int potsSize;
                try {
                    quantity = Integer.parseInt(txtQuantity.getText());
                    potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Quantity and Pot Size must be numbers!").show();
                    return;
                }
                String quantityText = txtQuantity.getText();
                String ingredients = txtIngredients.getText();

                boolean isValidQuantity = quantityText.matches(quantityPattern);
                boolean isValidIngredients = ingredients.matches(ingredientsPattern);

                if (!isValidQuantity && !isValidIngredients) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input format!").show();
                    return;
                }

                CurdProductionDto curdProductionDto = new CurdProductionDto(
                        lblId.getText(),
                        txtProductionDate.getText(),
                        txtExpiryDate.getText(),
                        quantity,
                        potsSize,
                        txtIngredients.getText(),
                        comStorageId.getValue()
                );
                boolean isUpdated = CurdProductionModel.updateCurdProduction(curdProductionDto);
                if (isUpdated) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Updated successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Update failed!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }
    public void tableOnClick(MouseEvent mouseEvent) {
        CurdProductionDto curdProductionDto = (CurdProductionDto) tblCurdProduction.getSelectionModel().getSelectedItem();
        if(curdProductionDto != null){
            lblId.setText(curdProductionDto.getProductionId());
            txtProductionDate.setText(String.valueOf(curdProductionDto.getProductionDate()));
            txtExpiryDate.setText(String.valueOf(curdProductionDto.getExpiryDate()));
            txtQuantity.setText(String.valueOf(curdProductionDto.getQuantity()));
            comPotsSize.setValue(Integer.valueOf(String.valueOf(curdProductionDto.getPotsSize())));
            txtIngredients.setText(String.valueOf(curdProductionDto.getIngredients()));
            comStorageId.setValue(curdProductionDto.getStorageId());
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

    public void txtIngredientsChange(KeyEvent keyEvent) {
        String ingredients = txtIngredients.getText();
        boolean isValid = ingredients.matches(ingredientsPattern);
        if (!isValid) {
            txtIngredients.setStyle(txtIngredients.getStyle() + ";-fx-border-color: red");
        }else {
            txtIngredients.setStyle(txtIngredients.getStyle() + ";-fx-border-color: blue");
        }
    }

    public void comPotsSizeOnAction(ActionEvent actionEvent) {
        Integer selectedPotSize = (Integer) comPotsSize.getSelectionModel().getSelectedItem();
        System.out.println( selectedPotSize);
    }

    public void comStorageIdOnAction(ActionEvent actionEvent) {
        String selectedStorageId = (String) comStorageId.getSelectionModel().getSelectedItem();
        System.out.println(selectedStorageId);
    }
}