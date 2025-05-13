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
import lk.ijse.desktop.myfx.myfinalproject.Dto.QualityCheckDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.QualityCheckModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QualityCheckController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<QualityCheckDto, String> colAppearance;

    @FXML
    private TableColumn<QualityCheckDto, Integer> colCheckId;

    @FXML
    private TableColumn<QualityCheckDto, Integer> colCollectionId;

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
    private TextField txtCheckId;

    @FXML
    private TextField txtCollectionId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtFatContent;

    @FXML
    private TextField txtNotes;

    @FXML
    private TextField txtTemperature;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtCheckId.getText());
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

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int checkId = Integer.parseInt(txtCheckId.getText());
        int collectionId = Integer.parseInt(txtCollectionId.getText());
        double fatContent = Double.parseDouble(txtFatContent.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());
        QualityCheckDto qualityCheckDto = new QualityCheckDto(checkId,collectionId,txtAppearance.getText(),fatContent,temperature,txtDate.getText(),txtNotes.getText());

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
    private void clearFields() {
        loadTable();
        txtCheckId.setText("");
        txtCollectionId.setText("");
        txtAppearance.setText("");
        txtFatContent.setText("");
        txtTemperature.setText("");
        txtDate.setText("");
        txtNotes.setText("");
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
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void tableOnClick(MouseEvent mouseEvent) {
        QualityCheckDto qualityCheckDto = (QualityCheckDto) tblQualityCheck.getSelectionModel().getSelectedItem();
        if (qualityCheckDto != null) {
            txtCheckId.setText(String.valueOf(qualityCheckDto.getCheckId()));
            txtCollectionId.setText(String.valueOf(qualityCheckDto.getCollectionId()));
            txtAppearance.setText(qualityCheckDto.getAppearance());
            txtFatContent.setText(String.valueOf(qualityCheckDto.getFatContent()));
            txtTemperature.setText(String.valueOf(qualityCheckDto.getTemperature()));
            txtDate.setText(String.valueOf(qualityCheckDto.getDate()));
            txtNotes.setText(String.valueOf(qualityCheckDto.getNotes()));
        }
    }
}
