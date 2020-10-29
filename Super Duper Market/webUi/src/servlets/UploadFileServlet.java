package servlets;

import Exceptions.*;
import com.google.gson.Gson;
import utils.Constants;
import logic.SDMLogicInterface;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "servlets.UploadFileServlet", urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SDMLogicInterface sdmLogic=null;
        Map<String,String > mapToReturn = new HashMap<>();
        String errorMsg="";
        Gson gson = new Gson();

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
        String usernameFromSession = SessionUtils.getUsername(request);

        synchronized (getServletContext()) {
         sdmLogic = ServletUtils.getSdmLogic(getServletContext());

        }
        Part part = request.getPart("dataFile");
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        //todo: synchronize


            try {
                String newRegionName = null;
                synchronized (sdmLogic) {
                    newRegionName = sdmLogic.importDataFromXmlFile(part.getInputStream(), usernameFromSession, fileName);
                }
                mapToReturn.put("nextPage",Constants.REGIONS_PAGE);
                mapToReturn.put("newRegionName",newRegionName);
//                String mapToReturnJson=gson.toJson(mapToReturn);
//                out.print(mapToReturnJson);

            } catch (InvalidFileExtension e) {
                errorMsg+="Invalid file\nFile should end with extension "+e.getWantedExtension();
//                out.print(errorMsg);

            } catch (JAXBException e) {
                errorMsg+="An unexpected error occurred while generating the objects.\n";
//                out.print(errorMsg);

            } catch (ItemNotFoundInStoresException e) {
                errorMsg+="Invalid xml file\n";
                errorMsg+="Item with id " +  e.getItemNotFoundId() + " is not sold by any store in the system.\n";
                errorMsg+="Each item should be sold in at least one store in the system.\n";
//                out.print(errorMsg);

            } catch (ValueOutOfRangeException e) {
                errorMsg+="Invalid xml file\n";
                errorMsg+=e.getVariableName() + " of " + e.getObjectName() + "is out of range.\n";
                errorMsg+=e.getVariableName() + " should be between " + e.getMinValue() + " to " + e.getMaxValue()+"\n";
//                out.print(errorMsg);

            } catch (StoreItemNotFoundInSystemException e) {
                errorMsg+="Invalid xml file\n";
                if( e.isInSale())
                {
                    errorMsg+="The store " + e.getStoreName() + " put on sale item with id " +  e.getItemNotFoundId() + ", this item does not exist in the system.\n";
                    errorMsg+="A store can't put on sale an item that doesn't exist in the system.\n";
                }
                else
                {
                    errorMsg+="The store " +  e.getStoreName() + " sells an item with id " + e.getItemNotFoundId() + ", this item does not exist in the system.\n";
                    errorMsg+="A store can't sell an item that doesn't exist in the system.\n";
                }
//                out.print(errorMsg);

            } catch (ItemAlreadyExistInStoreException e) {
                errorMsg+="Invalid xml file\n";
                errorMsg+="A sell of item with id " + e.getItemId() + " is defined in the store "
                        +e.getStoreName()+ " more than once.\n";
                errorMsg+="Each item sell should be defined in each store at most once.\n";
//                out.print(errorMsg);

            } catch (DoubleObjectIdInSystemException e) {
                errorMsg += "Invalid xml file\n";
                errorMsg += "There are 2 " +  e.getPluralObjectName() + " in the xml file with the same id.\n";
                errorMsg += "Id should be unique value.\n";
//                out.print(errorMsg);

            } catch (DoubleObjectInCoordinateException e) {
                errorMsg += "Invalid xml file.\n";
                errorMsg +=  ((DoubleObjectInCoordinateException) e).getObjectString1()+" and "+((DoubleObjectInCoordinateException) e).getObjectString2()+" are in the same coordinate "+((DoubleObjectInCoordinateException) e).getLocation().toString()+".\n";
                errorMsg += "In each coordinate should be only one customer or one store or nothing.\n";
//                out.print(errorMsg);

            } catch (ItemInSaleNotFoundInStoreException e) {
            errorMsg += "Invalid xml file.\n";
            errorMsg+="The store " +  e.getStoreName() + " put on sale item with id " +  e.getItemId() + ", this item does not exist in the store.\n";
            errorMsg+="A store can't put on sale an item that doesn't exist in it.\n";
//                out.print(errorMsg);

            } catch (RegionAlreadyExistException e) {

                errorMsg += "Invalid xml file.\n";
                errorMsg+="The region "+e.getRegionName()+" already exist in system\n";
                errorMsg+="Region name should be unique";
//                out.print(errorMsg);

            }
            catch (Exception e)
            {
                errorMsg+= "An unexpected error occurred , please try again\n";
//                out.print(errorMsg);

            }
            finally {
                if(errorMsg!="")
                {
                    mapToReturn.put("errorMsg",errorMsg);
                }

                String mapToReturnJson=gson.toJson(mapToReturn);
                out.print(mapToReturnJson);
                out.flush();
            }
        }

    }
}
