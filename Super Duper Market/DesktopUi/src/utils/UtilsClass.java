package utils;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

import java.util.Optional;


public final class UtilsClass {

    public static String skin;
    public static final int NO_VALUE=-1;

    private UtilsClass() {
    }

    public static void setSkin(String homePageSkin)
    {
        skin = homePageSkin;
    }

    public static void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        switch (skin) {
            case "Light":
                dialogPane.getStylesheets().add("/utils/myDialogs.css");
                break;
            case "Dark":
                dialogPane.getStylesheets().add("/utils/myDialogsDark.css");
                break;
            case "Colorful":
                dialogPane.getStylesheets().add("/utils/myDialogsColorful.css");
                break;
        }
        dialogPane.getStyleClass().add("myDialog");

        alert.setTitle("Error Dialog");
        alert.setContentText(errorMsg);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        switch (skin) {
            case "Light":
                dialogPane.getStylesheets().add("/utils/myDialogs.css");
                break;
            case "Dark":
                dialogPane.getStylesheets().add("/utils/myDialogsDark.css");
                break;
            case "Colorful":
                dialogPane.getStylesheets().add("/utils/myDialogsColorful.css");
                break;
        }
        dialogPane.getStyleClass().add("myDialog");
        alert.setTitle("Error Dialog");
        alert.setContentText("Unexpected error occurred");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void showInformatioDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        switch (skin) {
            case "Light":
                dialogPane.getStylesheets().add("/utils/myDialogs.css");
                break;
            case "Dark":
                dialogPane.getStylesheets().add("/utils/myDialogsDark.css");
                break;
            case "Colorful":
                dialogPane.getStylesheets().add("/utils/myDialogsColorful.css");
                break;
        }
        dialogPane.getStyleClass().add("myDialog");
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static ButtonType showConfirmationDialof(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dialogPane = alert.getDialogPane();
        switch (skin) {
            case "Light":
                dialogPane.getStylesheets().add("/utils/myDialogs.css");
                break;
            case "Dark":
                dialogPane.getStylesheets().add("/utils/myDialogsDark.css");
                break;
            case "Colorful":
                dialogPane.getStylesheets().add("/utils/myDialogsColorful.css");
                break;
        }
        dialogPane.getStyleClass().add("myDialog");
        alert.setTitle("Confirmation Dialog");
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get();

    }

    public static String showInputDialog(String msg ) {
        String inputVal="";
        TextInputDialog dialog = new TextInputDialog();
        DialogPane dialogPane = dialog.getDialogPane();
        switch (skin) {
            case "Light":
                dialogPane.getStylesheets().add("/utils/myDialogs.css");
                break;
            case "Dark":
                dialogPane.getStylesheets().add("/utils/myDialogsDark.css");
                break;
            case "Colorful":
                dialogPane.getStylesheets().add("/utils/myDialogsColorful.css");
                break;
        }
        dialogPane.getStyleClass().add("myDialog");
        dialog.getEditor().getStyleClass().setAll("editor");

        dialog.setTitle("Input Dialog");
        dialog.setHeaderText(msg);
        //dialog.setContentText(msg);
        dialog.getEditor().setAlignment(Pos.BOTTOM_RIGHT);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            inputVal=result.get();
        }
        return inputVal;

    }

    public static void sleepForAWhile(long sleepTime) {
        if (sleepTime != 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {

            }
        }
    }



}
//
//// The Java 8 way to get the response value (with lambda expression).
//result.ifPresent(name -> System.out.println("Your name: " + name));




