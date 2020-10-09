//package components.zxzx.uploadFile;
//
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import superDuperMarket.SuperDuperMarket;
//
//import java.awt.*;
//import java.io.File;
//
//public class UploadFileController {
//
//    @FXML
//    private Button uploadButton;
//
//    @FXML
//    private Label fileNme;
//
//
//    private SimpleStringProperty selectedFileProperty;
//    private SimpleBooleanProperty isFileSelected;
//
//    private SuperDuperMarket sdmLogic;
////    private Stage parentStage;
//    private Stage primaryStage;
//
////todo ctor
//    public UploadFileController() {
//
//    }
//
//    public void setSDMLogic(SuperDuperMarket sdmLogic) {
//        this.sdmLogic = sdmLogic;
//        //businessLogic.fileNameProperty().bind(selectedFileProperty);
//    }
//
//    public void setPrimaryStage(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//    }
//
//    @FXML
//    private void initialize() {
//        uploadButton.disableProperty().bind(isFileSelected.not());
//    }
//
//
//
//
//    @FXML
//    public void openFileButtonAction(javafx.event.ActionEvent actionEvent) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select data file");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//        if (selectedFile == null) {
//            return;
//        }
//
//        String absolutePath = selectedFile.getAbsolutePath();
//        selectedFileProperty.set(absolutePath);
//        isFileSelected.set(true);
//    }
//}
//
//


package components.uploadFile;

import Exceptions.*;
import components.homePage.HomePageController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import superDuperMarket.SuperDuperMarket;
import tasks.LoadXmlTask;
import utils.UtilsClass;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFileController {

    @FXML
    private Button uploadButton;

    @FXML
    private Label fileNameLbl;

    @FXML
    private ProgressBar fileProgressBar;

    @FXML
    private Label progressMsg;

    private Stage primaryStage;
    private HomePageController homePageController;
    private File xmlFileData;
    private String fileName;


    private final SimpleBooleanProperty isFileSelectedProperty;
    private final SimpleStringProperty selectedFileNameProperty;


    public UploadFileController() {
        isFileSelectedProperty = new SimpleBooleanProperty(false);
        selectedFileNameProperty = new SimpleStringProperty("No file selected");
        //homePageController=new HomePageController();
    }

    @FXML
    void openFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        this.xmlFileData = selectedFile;
        this.fileName = getFileName(selectedFile);
        selectedFileNameProperty.set(this.fileName);
        isFileSelectedProperty.set(true);
        fileProgressBar.setVisible(false);
    }

//        public void setSDMLogic(SuperDuperMarket sdmLogic) {
//        this.sdmLogic = sdmLogic;
    //businessLogic.fileNameProperty().bind(selectedFileProperty);
//    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        uploadButton.disableProperty().bind(isFileSelectedProperty.not());
        fileNameLbl.textProperty().bind(selectedFileNameProperty);

        //fileProgressBar.visibleProperty().bind(isFileSelectedProperty);
    }

    @FXML
    void UploadFile(ActionEvent event) throws InterruptedException {

        LoadXmlTask currentRunningTask = new LoadXmlTask(this.xmlFileData);
        fileProgressBar.setVisible(true);
        fileProgressBar.progressProperty().bind(currentRunningTask.progressProperty());
        progressMsg.textProperty().bind(currentRunningTask.messageProperty());

        Thread currTread = new Thread(currentRunningTask);
        currTread.start();
        final SuperDuperMarket[] sdm = {null};


        currentRunningTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {

                sdm[0] = currentRunningTask.getValue();
                homePageController.setSDMLogic(sdm[0]);
                homePageController.updateFileNameLblText(fileName);
                homePageController.fileSelected(); //ALONA
                homePageController.getHomePageBorderPane().setCenter(null);
                primaryStage.close();
            }
        });

        currentRunningTask.setOnFailed(e -> {
            Throwable exc = currentRunningTask.getException();
            ShowErrorDialog(exc);
        });
    }

    public void ShowErrorDialog(Throwable e ) {

//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Error Dialog");
        String errorMsg = "";
        if (e instanceof ItemNotFoundInStoresException) {
            errorMsg+="Invalid xml file\n";
            errorMsg+="Item with id " + ((ItemNotFoundInStoresException) e).getItemNotFoundId() + " is not sold by any store in the system.\n";
            errorMsg+="Each item should be sold in at least one store in the system.\n";

         } else if (e instanceof ValueOutOfRangeException) {
            ValueOutOfRangeException valueOutOfRangeExc=(ValueOutOfRangeException) e;
            errorMsg+="Invalid xml file\n";
            errorMsg+=valueOutOfRangeExc.getVariableName() + " of " + valueOutOfRangeExc.getObjectName() + "is out of range.\n";
            errorMsg+=valueOutOfRangeExc.getVariableName() + " should be between " + valueOutOfRangeExc.getMinValue() + " to " + valueOutOfRangeExc.getMaxValue()+"\n";

         } else if (e instanceof StoreItemNotFoundInSystemException) {

            errorMsg+="Invalid xml file\n";
            if(((StoreItemNotFoundInSystemException) e).isInSale())
            {
                errorMsg+="The store " + ((StoreItemNotFoundInSystemException) e).getStoreName() + " put on sale item with id " + ((StoreItemNotFoundInSystemException) e).getItemNotFoundId() + ", this item does not exist in the system.\n";
                errorMsg+="A store can't put on sale an item that doesn't exist in the system.\n";
            }
            else
            {
                errorMsg+="The store " + ((StoreItemNotFoundInSystemException) e).getStoreName() + " sells an item with id " + ((StoreItemNotFoundInSystemException) e).getItemNotFoundId() + ", this item does not exist in the system.\n";
                errorMsg+="A store can't sell an item that doesn't exist in the system.\n";
            }


         } else if (e instanceof ItemAlreadyExistInStoreException) {
            ItemAlreadyExistInStoreException itemAlreadyExistInStoreExc=(ItemAlreadyExistInStoreException) e;
            errorMsg+="Invalid xml file\n";
            errorMsg+="A sell of item with id " + itemAlreadyExistInStoreExc.getItemId() + " is defined in the store "
                                +itemAlreadyExistInStoreExc.getStoreName()+ " more than once.\n";
            errorMsg+="Each item sell should be defined in each store at most once.\n";

         } else if (e instanceof JAXBException) {
            errorMsg+="Invalid xml file\n";
            errorMsg+="An unexpected error occurred while generating the objects.\n";

         }  else if (e instanceof DoubleObjectIdInSystemException) {
            errorMsg += "Invalid xml file\n";
            errorMsg += "There are 2 " + ((DoubleObjectIdInSystemException) e).getPluralObjectName() + " in the xml file with the same id.\n";
            errorMsg += "Id should be unique value.\n";


        }  else if (e instanceof DoubleObjectInCoordinateException) {
        errorMsg += "Invalid xml file.\n";
        errorMsg +=  ((DoubleObjectInCoordinateException) e).getObjectString1()+" and "+((DoubleObjectInCoordinateException) e).getObjectString2()+" are in the same coordinate "+((DoubleObjectInCoordinateException) e).getLocation().toString()+".\n";
        errorMsg += "In each coordinate should be only one customer or one store or nothing.\n";
        }  else if (e instanceof ItemInSaleNotFoundInStoreException) {
            errorMsg += "Invalid xml file.\n";
            errorMsg+="The store " + ((ItemInSaleNotFoundInStoreException) e).getStoreName() + " put on sale item with id " + ((ItemInSaleNotFoundInStoreException) e).getItemId() + ", this item does not exist in the store.\n";
            errorMsg+="A store can't put on sale an item that doesn't exist in it.\n";
        }

        else {
            errorMsg += "An unexpected error occurred , please try again\n";
        }
        UtilsClass.showErrorAlert(errorMsg);
//        alert.setContentText(errorMsg);
//        alert.initOwner(primaryStage);
//        alert.initModality(Modality.WINDOW_MODAL);
//        alert.showAndWait();

    }

        //todo
//    void createUiAdapter()
//    {
//
//    }

        public void setHomePageController (HomePageController homePageController)
        {
            this.homePageController = homePageController;
        }

        public String getFileName (File currFile)
        {
            String absolutePath = currFile.getAbsolutePath();

            // create object of Path
            Path path = Paths.get(absolutePath);

            // call getFileName() and get FileName path object
            String fileName = path.getFileName().toString();

            return fileName;
        }
    }

