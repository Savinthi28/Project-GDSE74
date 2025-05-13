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
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
   public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(txtId.getText());
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
        txtId.setText("");
        txtName.setText("");
        txtPassword.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void tableOnClick(MouseEvent mouseEvent) {

    }
}
