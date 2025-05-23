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
import lk.ijse.desktop.myfx.myfinalproject.Dto.QualityCheckDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.QualityCheckModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QualityCheckController implements Initializable {

    public AnchorPane getAncQualityCheck(){
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadQualityCollectionId();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadQualityCollectionId() throws SQLException {
        ArrayList<String> collectionId = QualityCheckModel.getAllQualityCollectionId();
        ObservableList<String> observableList = FXCollections.observableArrayList(collectionId);
        observableList.addAll(collectionId);
        comCollectionId.setItems(observableList);
    }


    @FXML
    private AnchorPane ancQualityCheck;
    private String path;

    @FXML
    private TableColumn<QualityCheckDto, String> colAppearance;

    @FXML
    private TableColumn<QualityCheckDto, String> colCheckId;

    @FXML
    private TableColumn<QualityCheckDto, String> colCollectionId;

    @FXML
    private TableColumn<QualityCheckDto, String> colDate;

    @FXML
    private TableColumn<QualityCheckDto, Double> colFatContent;

    @FXML
    private TableColumn<QualityCheckDto, String> colNotes;

    @FXML
    private TableColumn<QualityCheckDto, Double> colTemperature;

    @FXML
    private TableView<QualityCheckDto> tblQualityCheck;

    @FXML
    private TextField txtAppearance;

    @FXML
    private Label lblId;

    @FXML
    private ComboBox<String> comCollectionId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtFatContent;

    @FXML
    private TextField txtNotes;

    @FXML
    private TextField txtTemperature;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();
        try {
            boolean isDelete = new QualityCheckModel().deleteQualityCheck(new QualityCheckDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Quality Check Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Quality Check Deletion Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Quality Check Deletion Failed").show();
        }
    }

    private void loadNextId() throws SQLException {
        QualityCheckModel qualityCheckModel = new QualityCheckModel();
        String id = qualityCheckModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        double fatContent = Double.parseDouble(txtFatContent.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());
        QualityCheckDto qualityCheckDto = new QualityCheckDto(lblId.getText(),comCollectionId.getValue(),txtAppearance.getText(),fatContent,temperature,txtDate.getText(),txtNotes.getText());

        try {
            QualityCheckModel qualityCheckModel = new QualityCheckModel();
            boolean isSaved = qualityCheckModel.saveQualityCheck(qualityCheckDto);
            if (isSaved) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Quality Check Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Quality Check Not Saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Quality Check Not Saved").show();
        }
    }
    private void clearFields() throws SQLException {
        loadTable();
        lblId.setText("");
        comCollectionId.setValue("");
        txtAppearance.setText("");
        txtFatContent.setText("");
        txtTemperature.setText("");
        txtDate.setText("");
        txtNotes.setText("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
        });
    }
    private void loadTable() {
        colCheckId.setCellValueFactory(new PropertyValueFactory<>("checkId"));
        colCollectionId.setCellValueFactory(new PropertyValueFactory<>("collectionId"));
        colAppearance.setCellValueFactory(new PropertyValueFactory<>("appearance"));
        colFatContent.setCellValueFactory(new PropertyValueFactory<>("fatContent"));
        colTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        try {
            QualityCheckModel qualityCheckModel = new QualityCheckModel();
            ArrayList<QualityCheckDto> qualityCheckDtos = qualityCheckModel.viewAllQualityCheck();
            if (qualityCheckDtos != null) {
                ObservableList<QualityCheckDto> observableList = FXCollections.observableArrayList(qualityCheckDtos);
                tblQualityCheck.setItems(observableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        double fatContent = Double.parseDouble(txtFatContent.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());
        QualityCheckDto qualityCheckDto = new QualityCheckDto(lblId.getText(),comCollectionId.getValue(),txtAppearance.getText(),fatContent,temperature,txtDate.getText(),txtNotes.getText());
        try {
            boolean isSave = QualityCheckModel.updateQualityCheck(qualityCheckDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Quality Check Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Quality Check Not Updated").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Quality Check Not Updated").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        QualityCheckDto qualityCheckDto = (QualityCheckDto) tblQualityCheck.getSelectionModel().getSelectedItem();
        if (qualityCheckDto != null) {
            lblId.setText(qualityCheckDto.getCheckId());
            comCollectionId.setValue(qualityCheckDto.getCollectionId());
            txtAppearance.setText(qualityCheckDto.getAppearance());
            txtFatContent.setText(String.valueOf(qualityCheckDto.getFatContent()));
            txtTemperature.setText(String.valueOf(qualityCheckDto.getTemperature()));
            txtDate.setText(String.valueOf(qualityCheckDto.getDate()));
            txtNotes.setText(String.valueOf(qualityCheckDto.getNotes()));
        }
    }

    public void btnGoToMilkCollectionOnAction(ActionEvent actionEvent) {
        navigateTo("/View/MilkCollectionView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancQualityCheck.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancQualityCheck.widthProperty());
            anchorPane.prefHeightProperty().bind(ancQualityCheck.heightProperty());
            ancQualityCheck.getChildren().add(anchorPane);
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

    public void comCollectionIdOnAction(ActionEvent actionEvent) {
        String selectedCollectionId = (String) comCollectionId.getSelectionModel().getSelectedItem();
        System.out.println(selectedCollectionId);
    }
}
