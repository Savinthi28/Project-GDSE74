package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public AnchorPane getAncMainContainer() {

        return null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Dashboard load, I'm initializer");
        //navigateTo("/View/DashboardView.fxml");

    }

    @FXML
    private AnchorPane ancMainContainer;
    private String path;

    @FXML
    void btnGoBuffaloOnAction(ActionEvent event) {
        navigateTo("/View/BuffaloView.fxml");
    }

    @FXML
    void btnGoCurdProductionOnAction(ActionEvent event) {
        navigateTo("/View/CurdProduction&StockView.fxml");
    }

    @FXML
    void btnGoCustomerOnAction(ActionEvent event) {
        navigateTo("/View/CustomerView.fxml");
    }

    @FXML
    void btnGoIMilkOnAction(ActionEvent event) {
        navigateTo("/View/Collection,Storage&QualityCheckView.fxml");
    }

    @FXML
    void btnGoIncomeOnAction(ActionEvent event) {
        navigateTo("/View/Income&ExpenseView.fxml");
    }

    @FXML
    void btnGoOrderOnAction(ActionEvent event) {
        navigateTo("/View/OrderView.fxml");
    }

    @FXML
    void btnGoPaymentOnAction(ActionEvent event) {
        navigateTo("/View/PaymentView.fxml");
    }

    @FXML
    void btnGoReportOnAction(ActionEvent event) {
        navigateTo("/View/ReportsView.fxml");
    }

    @FXML
    void btnGoSupplierOnAction(ActionEvent event) {
        navigateTo("/View/Supplier,PotsIn,PotsPur,RawMaterialView.fxml");
    }

    @FXML
    void btnGoUserOnAction(ActionEvent event) {
        navigateTo("/View/UserView.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());

            ancMainContainer.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong while loading the main container", ButtonType.OK).show();
            e.printStackTrace();
        }

    }

}
