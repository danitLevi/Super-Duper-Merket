//import Exceptions.*;
//import generatedClassesJaxb.SuperDuperMarketDescriptor;
//import superDuperMarket.SuperDuperMarket;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//
//public final class LoadXml
//{
//    public static SuperDuperMarket importDataFromXmlFile(String filePath ) throws InvalidFileExtension, FileNotFoundException, JAXBException, ItemNotFoundInStoresException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException {
//        SuperDuperMarket superDuperMarketObj = null;
////            if (!getFileExtension(filePath).equals("xml"))
////            {
////                throw new InvalidFileExtension("xml");
////            }
////
//            File xmlFile = new File(filePath);
//            if(!xmlFile.exists())
//            {
//                throw new FileNotFoundException();
//            }
//            InputStream inputStream = new FileInputStream(xmlFile);
//            SuperDuperMarketDescriptor superDuperMarketJaxbObj = deserializeFrom(inputStream);
//
//
//            superDuperMarketObj=new SuperDuperMarket(superDuperMarketJaxbObj);
//
//
//        return superDuperMarketObj;
//    }
//
//    private static SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
//        JAXBContext jc = JAXBContext.newInstance("jaxb.generated");
//        Unmarshaller u = jc.createUnmarshaller();
//        return (SuperDuperMarketDescriptor) u.unmarshal(in);
//    }
//
//    private static String getFileExtension(String filePath)
//    {
//
//        int lastIndexOf = filePath.lastIndexOf('.');
//        if (lastIndexOf == -1) {
//            return "";  // Empty extension
//        }
//        return filePath.substring(lastIndexOf+1).toLowerCase();
//    }
//}
