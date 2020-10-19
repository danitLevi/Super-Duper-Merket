package servlets;

import Exceptions.*;
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
import java.io.PrintWriter;

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
        response.setContentType("text/html;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        synchronized (getServletContext()) {
         sdmLogic = ServletUtils.getSdmLogic(getServletContext());
        }
        Part part = request.getPart("dataFile");

        //todo: sync
        try (PrintWriter out = response.getWriter()){

            try {
                sdmLogic.importDataFromXmlFile(part.getInputStream(),usernameFromSession);
                out.print(Constants.REGIONS_PAGE);     //On success.

            } catch (InvalidFileExtension invalidFileExtension) {
                out.print("my error");
            } catch (JAXBException e) {
                out.print("my error");
            } catch (ItemNotFoundInStoresException e) {
                out.print("my error");
            } catch (ValueOutOfRangeException e) {
                out.print("my error");
            } catch (StoreItemNotFoundInSystemException e) {
                out.print("my error");
            } catch (ItemAlreadyExistInStoreException e) {
                out.print("my error");
            } catch (DoubleObjectIdInSystemException e) {
                out.print("my error");
            } catch (DoubleObjectInCoordinateException e) {
                out.print("my error");
            } catch (ItemInSaleNotFoundInStoreException e) {
                out.print("my error");
            } catch (RegionAlreadyExistException e) {
                out.print("my error");
            }
            out.flush();
        }

//        catch (JAXBException e) {
//            e.printStackTrace();
//        } catch (ValueOutOfRangeException e) {
//            out.print("myError");
//        } catch (StoreItemNotFoundInSystemException e) {
//            e.printStackTrace();
//        } catch (ItemAlreadyExistInStoreException e) {
//            e.printStackTrace();
//        } catch (DoubleObjectIdInSystemException e) {
//            e.printStackTrace();
//        } catch (DoubleObjectInCoordinateException e) {
//            e.printStackTrace();
//        } catch (ItemInSaleNotFoundInStoreException e) {
//            e.printStackTrace();
//        } catch (ItemNotFoundInStoresException e) {
//            e.printStackTrace();
//        } catch (InvalidFileExtension invalidFileExtension) {
//            invalidFileExtension.printStackTrace();
//        } catch (RegionAlreadyExistException e) {
//            e.printStackTrace();
//        }


    }
}
