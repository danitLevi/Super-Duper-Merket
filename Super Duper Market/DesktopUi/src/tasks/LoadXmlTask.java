package tasks;

import Exceptions.*;
import LoadData.LoadSdmData;
import generatedClassesJaxb.SuperDuperMarketDescriptor;
import javafx.concurrent.Task;
import superDuperMarket.SuperDuperMarket;
import utils.UtilsClass;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static LoadData.LoadSdmData.generateDataFromInputStream;
import static LoadData.LoadSdmData.importDataFromDescriptor;


public class LoadXmlTask extends Task<SuperDuperMarket> {

    //    private String filePath;
    private final File xmlDataFile;



    public LoadXmlTask(File xmlDataFile) {
        this.xmlDataFile = xmlDataFile;
    }

/*    @Override
    protected SuperDuperMarket call() {


    }*/


//    private static void sleepForAWhile(long sleepTime) {
//        if (sleepTime != 0) {
//            try {
//                Thread.sleep(sleepTime);
//            } catch (InterruptedException ignored) {
//
//            }
//        }
//    }


    @Override
    protected SuperDuperMarket call() throws JAXBException, FileNotFoundException, StoreItemNotFoundInSystemException, ItemNotFoundInStoresException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, ValueOutOfRangeException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException {
        //superDuperMarket.SuperDuperMarket sdmLogic=null;

        updateProgress(0, 1);

        updateMessage("uploading file...");
        UtilsClass.sleepForAWhile(1000);

        InputStream inputStream = null;

        inputStream = LoadSdmData.loadFile(this.xmlDataFile);

        updateProgress(0.33, 1);
        UtilsClass.sleepForAWhile(1000);

        updateMessage("Generate objects...");
        SuperDuperMarketDescriptor smdDescriptor = null;

        smdDescriptor = generateDataFromInputStream(inputStream);

        updateProgress(0.66, 1);
        UtilsClass.sleepForAWhile(1000);

        updateMessage("Validate data...");
        SuperDuperMarket sdmLogic = importDataFromDescriptor(smdDescriptor);
        updateProgress(1, 1);
        UtilsClass.sleepForAWhile(1000);

        return sdmLogic;

    }
}
