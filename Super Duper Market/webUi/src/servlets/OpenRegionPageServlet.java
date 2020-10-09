package servlets;

import constants.Constants;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servlets.RegionPageServlet", urlPatterns = {"/regionPage"})

public class OpenRegionPageServlet  extends HttpServlet {

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req,resp);
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String regionName = (String) request.getParameter("name");
            SessionUtils.setRegionName(request, regionName);

            //todo : use redirect instead ?
            out.print(Constants.ONE_REGION_PAGE);
            out.flush();
        }
//        response.sendRedirect(request.getContextPath() +"/"+ Constants.ONE_REGION_PAGE);
//        return;
    }
}
