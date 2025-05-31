package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.desktop.myfx.myfinalproject.Dto.UserDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.UserModel;
import lk.ijse.desktop.myfx.myfinalproject.Util.EmailSender; // අලුත් Utility class එක import කරගන්නවා

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML
    private TextField txtEmail;

    @FXML
    private AnchorPane forgotPasswordPane; // UI එක load කරන AnchorPane එක.

    @FXML
    void btnSendPasswordOnAction(ActionEvent event) {
        String email = txtEmail.getText();

        if (email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "කරුණාකර ඔබගේ ඊමේල් ලිපිනය ඇතුලත් කරන්න.").show();
            return;
        }

        try {
            UserModel userModel = new UserModel();
            UserDto userDto = userModel.getUserByEmail(email); // ඊමේල් එකෙන් user කෙනෙක් ඉන්නවද කියලා බලනවා

            if (userDto != null) {
                // ඊමේල් එක හමුවුනා, password එක යවන්න
                String subject = "ඔබගේ MyFinalProject සඳහා වන මුරපදය";
                String body = "ආදරණීය " + userDto.getUserName() + ",\n\nඔබගේ මුරපදය: " + userDto.getPassword() + "\n\nසුභ පැතුම්,\nMyFinalProject කණ්ඩායම";

                boolean emailSent = EmailSender.sendEmail(email, subject, body); // EmailSender utility එක භාවිතා කරනවා

                if (emailSent) {
                    new Alert(Alert.AlertType.INFORMATION, "ඔබගේ මුරපදය ඔබගේ ඊමේල් ලිපිනයට යවන ලදී.").show();
                    // අවශ්‍ය නම්, නැවත login page එකට යන්න පුළුවන්
                    backToLoginPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "ඊමේල් යැවීම අසාර්ථක විය. කරුණාකර නැවත උත්සාහ කරන්න.").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "මෙම ඊමේල් ලිපිනයෙන් කිසිදු පරිශීලකයෙකු සොයාගත නොහැක.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "දත්ත සමුදා දෝෂයක්: " + e.getMessage()).show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "පිටු මාරු කිරීමේ දෝෂයක්: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        backToLoginPage();
    }

    private void backToLoginPage() throws IOException {
        forgotPasswordPane.getChildren().clear();
        // ඔබගේ login FXML file එකේ නම මෙතනට දෙන්න. සාමාන්‍යයෙන් LoginPageView.fxml
        Parent parent = FXMLLoader.load(getClass().getResource("/View/LoginView.fxml"));
        forgotPasswordPane.getChildren().add(parent);
    }
}