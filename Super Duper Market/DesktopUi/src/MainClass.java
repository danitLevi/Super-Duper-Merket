//import examples.basic.tasks.common.HistogramResourcesConstants;
//import examples.basic.tasks.components.main.HistogramController;
//import examples.basic.tasks.logic.BusinessLogic;

import components.homePage.HomePageConstants;
import components.homePage.HomePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

//import org.fxmisc.cssfx.CSSFX;

public class MainClass extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


//        File j= new File(HomePageConstants.HOME_PAGE_FXML_RESOURCE_IDENTIFIER);
//        if(j.exists())
//        {
//            System.out.println("Ok");
//        }
//        else
//        {
//            System.out.println("not ok");
//        }
        //CSSFX.start();
        
        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource(HomePageConstants.HOME_PAGE_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(mainFXML);
        Parent root = loader.load();

        // wire up controller
        HomePageController homePageController = loader.getController();
        homePageController.setPrimaryStage(primaryStage);

        //todo : add after load xml
//        SuperDuperMarket sdmLogic = new SuperDuperMarket(histogramController); // todo: generate jaxb
//        HomePageController.se(businessLogic);


        // set stage
        primaryStage.setTitle("Super Duper Market");
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {

        launch(args);
    }
}
