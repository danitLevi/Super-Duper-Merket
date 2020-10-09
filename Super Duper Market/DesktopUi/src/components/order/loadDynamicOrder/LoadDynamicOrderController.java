package components.order.loadDynamicOrder;


import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import superDuperMarket.LogicInterface;
import tasks.CalculateMinimalBagTask;

import java.util.Map;
import java.util.function.Consumer;

public class LoadDynamicOrderController {


    @FXML
    private ProgressIndicator progress;

    @FXML
    private Label progressMsgLbl;

    private Stage primaryStage;

    private Map<Integer, Map<Integer, Double>> itemsMinimalBag=null;


    public void findMinimalBag(Map<Integer, Double> itemIdToAmount, LogicInterface sdmLogic, Consumer<Map<Integer, Map<Integer, Double>>> setMinimalPriceBag,Consumer<Boolean> isMinimalPriceBagReturned) throws InterruptedException {
        CalculateMinimalBagTask currentRunningTask = new CalculateMinimalBagTask(itemIdToAmount,sdmLogic);
        progress.progressProperty().bind(currentRunningTask.progressProperty());
        progressMsgLbl.textProperty().bind(currentRunningTask.messageProperty());
        currentRunningTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                setMinimalPriceBag.accept(currentRunningTask.getValue());
                isMinimalPriceBagReturned.accept(true);
//                itemsMinimalBag=currentRunningTask.getValue();
                primaryStage.close();
//                return itemsMinimalBag;

            }
        });
//        currentRunningTask.setOnFailed(e -> {
////            Throwable exc = currentRunningTask.getException();
//            UtilsClass.showErrorAlert();
//        });
        Thread currTread = new Thread(currentRunningTask);

        currTread.start();

//        currTread.join();

//        currentRunningTask.valueProperty().addListener((observable, oldValue, newValue) -> {
//            return itemsMinimalBag;
//        });
//        return itemsMinimalBag; ///????


    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Map<Integer, Map<Integer, Double>> getItemsMinimalBag() {
        return itemsMinimalBag;
    }

    public void setItemsMinimalBag(Map<Integer, Map<Integer, Double>> itemsMinimalBag) {
        this.itemsMinimalBag = itemsMinimalBag;
    }
}

