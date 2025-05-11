package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private AnchorPane ancPage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        String password = txtPassword.getText();
        String userName = txtUserName.getText();

        if (password.equals("123") && userName.equals("savi")) {
            ancPage.getChildren().clear();

            Parent parent = FXMLLoader.load(getClass().getResource("/View/DashboardView.fxml"));
            ancPage.getChildren().add(parent);
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").show();
            System.out.println("Invalid Username or Password");
        }
    }

}
