package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.UserDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TableColumn<UserDto, Integer> colId;

    @FXML
    private TableColumn<UserDto, String> colName;

    @FXML
    private TableColumn<UserDto, String> colPassword;

    @FXML
    private TableView<UserDto> tblUser;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        try {
            boolean isDelete = new UserModel().deleteUser(new UserDto(id));
            if (isDelete) {
                clearFields();
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

    private void loadNextId() throws SQLException {
        UserModel userModel = new UserModel();
        String id = userModel.getNextId();
        lblId.setText(id);
    }

    @FXML
   public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(lblId.getText());
        UserDto userDto = new UserDto(id, txtName.getText(), txtPassword.getText());

        try {
            UserModel userModel = new UserModel();
            boolean isSave = userModel.saveUser(userDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "User has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "User has not been saved").show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User has not been saved").show();
        }
    }
    private void clearFields() {
        loadTable();
        lblId.setText("");
        txtName.setText("");
        txtPassword.setText("");
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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        try {
            UserModel userModel = new UserModel();
            ArrayList<UserDto> userDtos = userModel.viewAllUser();
            if (userDtos != null) {
                ObservableList<UserDto> observableList = FXCollections.observableArrayList(userDtos);
                tblUser.setItems(observableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        UserDto userDto = new UserDto(id, txtName.getText(), txtPassword.getText());
        try {
            boolean isSave = UserModel.updateUser(userDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "User has been updated successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "User has not been updated").show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User has not been updated").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        UserDto userDto = (UserDto) tblUser.getSelectionModel().getSelectedItem();
        if (userDto != null) {
            lblId.setText(String.valueOf(userDto.getId()));
            txtName.setText(userDto.getUserName());
            txtPassword.setText(userDto.getPassword());
        }

    }
}
