import daos.AuctionDAO;
import daos.PropertyDAO;
import objs.Auction;
import objs.Database;
import objs.Property;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "EditAuctionServlet")

public class EditAuctionServlet extends HttpServlet {
    private AuctionDAO auctionDAO;
    private PropertyDAO propertyDAO;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.auctionDAO = new AuctionDAO(database);
        this.propertyDAO = new PropertyDAO(database);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Auction auction = auctionDAO.retrieve(itemID);
            request.setAttribute("auction", auction);
            request.getRequestDispatcher("editAuction.jsp").include(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        response.setContentType("text/html");

        try {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Auction a= auctionDAO.retrieve(itemID);
            request.setAttribute("auction", a);
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");
            String catID = request.getParameter("catID");
            String startingBid = request.getParameter("startingBid");
            String reserve = request.getParameter("reserve");

            if(name.equals("") || desc.equals("") || catID.equals("")){
                request.setAttribute("error", "Empty field(s).");
                request.getRequestDispatcher("editAuction.jsp").include(request, response);
            } else {
                a.setItemName(name);
                a.setItemDescription(desc);
                a.setCatId(Integer.parseInt(catID));
                if (startingBid != null && reserve!= null && !startingBid.equals("") && !reserve.equals("")){
                    a.setStartingBid(Float.parseFloat(startingBid));
                    a.setReserveBid(Float.parseFloat(reserve));
                    a.setBidIncrement(Auction.getBidIncrement(Float.parseFloat(startingBid)));
                }
                a.setCurrentBid(0);
                a.setOpen(true);

                if(a.getCurrentBid() == 0 && a.getStartingBid() > (0 + a.getBidIncrement())) {
                    if (a.getReserveBid() <= a.getStartingBid()) {
                        a.setReserveBid(a.getStartingBid());
                    }

                    String propertyValue = null;
                    String propertyTag = "prop.";
                    int numberOfProperties = (Integer) session.getAttribute("numberOfProperties");
                    List<Property> itemProperties = new ArrayList<Property>();
                    for (int i = 1; i < numberOfProperties; i++) {
                        StringBuilder sb = new StringBuilder(String.valueOf(propertyTag));
                        sb.append(i);
                        String newStr = sb.toString();
                        propertyValue = request.getParameter(newStr);
                        if (propertyValue != null && !propertyValue.equals("")) {
                            Property property = new Property();
                            property.setPropertyID(i);
                            property.setValue(propertyValue);
                            itemProperties.add(property);
                        }
                    }

                    if (auctionDAO.editAuction(a)) {
                        propertyDAO.updateProperties(a.getItemID(), itemProperties);

                        request.setAttribute("message", "Auction successfully posted!");
                        request.setAttribute("auction", a);
                        request.getRequestDispatcher("editAuction.jsp").include(request, response);
                    } else {
                        request.setAttribute("message", "Auction failed.");
                        request.getRequestDispatcher("editAuction.jsp").include(request, response);
                    }

                }else{
                    request.setAttribute("error", "Invalid Bids or Blocking Bids");
                    request.getRequestDispatcher("editAuction.jsp").include(request, response);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}