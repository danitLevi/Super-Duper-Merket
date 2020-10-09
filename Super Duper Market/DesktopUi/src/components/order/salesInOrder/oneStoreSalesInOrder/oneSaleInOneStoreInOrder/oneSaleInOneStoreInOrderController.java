package components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder;

import DtoObjects.OfferDto;
import DtoObjects.SaleDto;
import components.order.salesInOrder.oneStoreSalesInOrder.OneStoreSalesInOrderController;
import components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.selectOffer.SelectOfferConstants;
import components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.selectOffer.SelectOfferController;
import components.sale.SaleConstants;
import components.sale.SaleController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;

public class oneSaleInOneStoreInOrderController {
    @FXML
    private Label timesToUseLbl;

    @FXML
    private Button useSale;
    @FXML
    private GridPane gridPane;

    private  OneStoreSalesInOrderController parentController;
    private LogicInterface sdmLogic;
    private SaleDto saleDetails;
    private static String skin;
    private SimpleIntegerProperty approvalAmountToUseProperty;

    public oneSaleInOneStoreInOrderController() {
        this.approvalAmountToUseProperty = new SimpleIntegerProperty();
    }

    @FXML
    public  void initialize()
    {
        timesToUseLbl.textProperty().bind(approvalAmountToUseProperty.asString());
    }

    public void showData(SaleDto saleDetails, int approvalAmount)
    {
        try {
        this.saleDetails=saleDetails;
        FXMLLoader loader = new FXMLLoader();
        URL saleTileUrl = getClass().getResource(SaleConstants.SALE_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(saleTileUrl);
        BorderPane saleDataBorderPane=  loader.load();
        saleDataBorderPane.getStyleClass().clear();
        gridPane.add(saleDataBorderPane,0,0,2,1);

        SaleController saleController =loader.getController();
        saleController.setSdmLogic(this.sdmLogic);
        saleController.showData(saleDetails);
        approvalAmountToUseProperty.setValue(approvalAmount);

        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    @FXML
    void useSale(ActionEvent event) {

        if(saleDetails.getOptionToGet().equals("ONE-OF"))
        {
            openSelectOfferWindow();
        }
        else
        {
            saveUsedSaleMultipleOffers();

        }

    }

    public void decreaseApprovalAmountByOne()
    {
        approvalAmountToUseProperty.setValue(approvalAmountToUseProperty.get() -1 );
    }


    public OneStoreSalesInOrderController getParentController() {
        return parentController;
    }

    public void setParentController(OneStoreSalesInOrderController parentController) {
        this.parentController = parentController;
    }

    public int getApprovalAmountToUseProperty() {
        return approvalAmountToUseProperty.get();
    }

    public SimpleIntegerProperty approvalAmountToUsePropertyProperty() {
        return approvalAmountToUseProperty;
    }

    public void setApprovalAmountToUseProperty(int approvalAmountToUseProperty) {
        this.approvalAmountToUseProperty.set(approvalAmountToUseProperty);
    }


   public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }
    public static void setSkin(String skinType)
    {
        skin = skinType;
    }

    public void openSelectOfferWindow()
    {

        try {
            FXMLLoader loader = new FXMLLoader();

            // load main fxml
            URL windowFXML = getClass().getResource(SelectOfferConstants.SELECT_OFFER_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(windowFXML);
            Parent root = null;
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Select Offer");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            SelectOfferController controller=loader.getController();
            controller.setParentController(this);
            controller.setSdmLogic(this.sdmLogic);
            controller.setPrimaryStage(stage);
            controller.showData(saleDetails.getOffersToGet());

            switch (skin) {
                case "Light":
                    String lightSkin = getClass().getResource(SelectOfferConstants.CSS_LIGHT_RESOURCE_IDENTIFIER).toExternalForm();
                    scene.getStylesheets().clear();
                    scene.getStylesheets().setAll(lightSkin);
                    break;
                case "Dark":
                    String darkSkin = getClass().getResource(SelectOfferConstants.CSS_DARK_RESOURCE_IDENTIFIER).toExternalForm();
                    scene.getStylesheets().clear();
                    scene.getStylesheets().setAll(darkSkin);
                    break;
                case "Colorful":
                    String colorSkin = getClass().getResource(SelectOfferConstants.CSS_COLORFUL_RESOURCE_IDENTIFIER).toExternalForm();
                    scene.getStylesheets().clear();
                    scene.getStylesheets().setAll(colorSkin);
                    break;
            }
            stage.show();
        } catch (IOException e) {
            UtilsClass.showErrorAlert();

        }
    }

    public void saveUsedSaleOffer(OfferDto offerDto)
    {
        parentController.addOfferToUsedOffersInStore(offerDto);
        notifyUsedSaleSaved();
    }

    public void saveUsedSaleMultipleOffers()
    {

        parentController.addOffersToUsedOffersInStore(saleDetails.getOffersToGet());
        notifyUsedSaleSaved();

    }

    public void notifyUsedSaleSaved()
    {

        UtilsClass.showInformatioDialog("The wanted items added to your order successfully");
        decreaseApprovalAmountByOne();
    }
}

