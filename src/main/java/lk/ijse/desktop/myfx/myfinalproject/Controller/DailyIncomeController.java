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
import lk.ijse.desktop.myfx.myfinalproject.Dto.DailyIncomeDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.DailyIncomeModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeController implements Initializable {
    public AnchorPane getAncDailyIncome(){
        return null;
    }

    @FXML
    private AnchorPane ancDailyIncome;
    private String path;

    @FXML
    private TableColumn<DailyIncomeDto, Double> colAmount;

    @FXML
    private TableColumn<DailyIncomeDto, String> colDate;

    @FXML
    private TableColumn<DailyIncomeDto, String> colDescription;

    @FXML
    private TableColumn<DailyIncomeDto, Integer> colId;

    @FXML
    private TableColumn<DailyIncomeDto, String> colName;

    @FXML
    private TableView<DailyIncomeDto> tblIncome;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtName;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFilds();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        try {
            boolean isDelete = new DailyIncomeModel().deleteDailyIncome(new DailyIncomeDto(id));
            if (isDelete) {
                clearFilds();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went error").show();
        }
    }

    private void loadNextId () throws SQLException {
        DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();
        String id = dailyIncomeModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        DailyIncomeDto dailyIncomeDto = new DailyIncomeDto(id,txtName.getText(),txtDate.getText(),txtDescription.getText(),amount);

        try {
            DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();
            boolean isSaved = dailyIncomeModel.saveDailyIncome(dailyIncomeDto);
            if (isSaved) {
                clearFilds();
                new Alert(Alert.AlertType.INFORMATION, "Income has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save income").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save income").show();
        }
    }
    private void clearFilds(){
        loadTable();
        lblId.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
    }
    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();
            ArrayList<DailyIncomeDto> dailyIncomeDtos = dailyIncomeModel.viewDailyIncome();
            if (dailyIncomeDtos != null) {
                ObservableList<DailyIncomeDto> data = FXCollections.observableArrayList(dailyIncomeDtos);
                tblIncome.setItems(data);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        DailyIncomeDto dailyIncomeDto = new DailyIncomeDto(id,txtName.getText(),txtDate.getText(),txtDescription.getText(),amount);
        try {
            boolean isSave = DailyIncomeModel.updateDailyIncome(dailyIncomeDto);
            if (isSave) {
                clearFilds();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save income").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save income").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        DailyIncomeDto dailyIncomeDto = (DailyIncomeDto) tblIncome.getSelectionModel().getSelectedItem();
        if (dailyIncomeDto != null) {
            lblId.setText(String.valueOf(dailyIncomeDto.getId()));
            txtName.setText(dailyIncomeDto.getCustomerName());
            txtDate.setText(dailyIncomeDto.getDate());
            txtDescription.setText(dailyIncomeDto.getDescription());
            txtAmount.setText(String.valueOf(dailyIncomeDto.getAmount()));
        }
    }

    public void btnGoToIncomeOnAction(ActionEvent actionEvent) {
        navigateTo("/View/DailyIncomeView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancDailyIncome.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancDailyIncome.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDailyIncome.heightProperty());
            ancDailyIncome.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }

    public void btnGoToExpenseOnAction(ActionEvent actionEvent) {
        navigateTo("/View/DailyExpenseView.fxml");
    }
}
