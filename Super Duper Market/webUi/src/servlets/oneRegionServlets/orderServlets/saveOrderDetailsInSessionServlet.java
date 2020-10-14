package servlets.oneRegionServlets.orderServlets;

import DtoObjects.ItemToOrderInputDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet(name = "servlets.oneRegionServlets.orderServlets.saveOrderDetailsInSessionServlet", urlPatterns = {"/saveOrderInputInSession"})

public class saveOrderDetailsInSessionServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<Integer, Map<Integer, Double>> orderMinimalPriceBag=new HashMap<>();
        Gson gson = new Gson();
//        Array arr=gson.to
//
//        String arr= String.valueOf(request.getInputStream());

        String arr=request.getParameter("items");

        Type type = new TypeToken<List<ItemToOrderInputDto>>() {}.getType();
        List<ItemToOrderInputDto> listFromJson = gson.fromJson(arr, type);



//        ObjectMapper mapper = new ObjectMapper();
//        itemToOrderInputDto dto = mapper.readValue(sortJson, SortDto.class);


//        JsonNode jsonNode = objectMapper.readTree(s);
//        JsonNode node = jsonNode.get(0);
//        String property = node.get("property").asText();
//        String direction = node.get("direction").asText();

//        JSONObject json = new JSONObject(jsonString);
//
//        ArrayList<ItemInStoreOrderDto> list = ArrayUtil.convert(jArray);

//        ArrayList<ItemInStoreOrderDto> jsonArray=
        //find bag
//
//        response.setContentType("application/json");
//        try (PrintWriter out = response.getWriter())
//        {
//            Gson gson = new Gson();
//            List<StoreDto> storesDetailsList=null;
//
//            synchronized (getServletContext()) {
//                SDMLogicInterface sdmLogic = ServletUtils.getSdmLogic(getServletContext());
//                String regionName= SessionUtils.getRegionName(request);
//                RegionInterface region=sdmLogic.getRegionByName(regionName);
//                storesDetailsList = region.getStoresDetails();
//            }
//            String json = gson.toJson(storesDetailsList);
//            out.println(json);
//            out.flush();
//        }

    }
}
