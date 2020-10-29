package servlets;

import DtoObjects.RegionBaseDataDto;
import com.google.gson.Gson;
import logic.SDMLogicInterface;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "servlets.RegionsBaseDataServlet", urlPatterns = {"/regionsBaseData"})

public class RegionsBaseDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            Gson gson = new Gson();
            List<RegionBaseDataDto> regionsBaseDetails=null;
            SDMLogicInterface sdmLogic =null;
            synchronized (getServletContext()) {
                sdmLogic = ServletUtils.getSdmLogic(getServletContext());
            }
            synchronized (sdmLogic) {
                regionsBaseDetails = sdmLogic.getAllRegionsBaseData();
            }

            String json = gson.toJson(regionsBaseDetails);
            out.println(json);
            out.flush();
        }

    }
}
