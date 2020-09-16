import daos.*;
import objs.*;
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

@WebServlet(name = "CreateAuctionServlet")

public class CreateAuctionServlet extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        boolean error = false;

        Auction a = new Auction();
        try {
            int sellerID = (Integer) session.getAttribute("userID");
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");
            String catID = request.getParameter("catID");
            String startingBid = request.getParameter("startingBid");
            String reserve = (request.getParameter("reserve"));
            String date = request.getParameter("date");
            String time = request.getParameter("time");

            if(name.equals("") || desc.equals("") || catID.equals("") || startingBid.equals("") || reserve.equals("") || date.equals("")||time.equals("")){
                request.setAttribute("error", "Empty field(s).");
                request.getRequestDispatcher("newAuction.jsp").include(request, response);
            }

            else {
                String endDate = date + " " + time;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date d = sdf.parse(endDate);
                Date current = new Date();

                if (d.compareTo(current) <= 0) {
                    request.setAttribute("error", "End date has already past.");
                    request.getRequestDispatcher("newAuction.jsp").include(request, response);
                }
                //request.setAttribute("Date", d);
                //request.setAttribute("catID", catID);
                else {
                    a.setItemName(name);
                    a.setItemDescription(desc);
                    a.setSellerID(sellerID);
                    a.setCatId(Integer.parseInt(catID));
                    a.setStartingBid(Float.parseFloat(startingBid));
                    a.setReserveBid(Float.parseFloat(reserve));
                    a.setEnd(d);
                    a.setBidIncrement(Auction.getBidIncrement(Float.parseFloat(startingBid)));
                    a.setCurrentBid(0);
                    a.setOpen(true);

                    if(a.getStartingBid() > (0 + a.getBidIncrement())){
                        if(a.getReserveBid() <= a.getStartingBid()){
                            a.setReserveBid(a.getStartingBid());
                        }
                        String propertyValue = null;
                        String propertyTag = "prop.";
                        int numberOfProperties = (Integer) session.getAttribute("numberOfProperties");
                        List<Property> itemProperties = new ArrayList<Property>();
                        for(int i = 1; i<numberOfProperties; i++){
                            StringBuilder sb = new StringBuilder (String.valueOf (propertyTag));
                            sb.append(i);
                            String newStr = sb.toString();
                            propertyValue = request.getParameter(newStr);
                            if(propertyValue!=null && !propertyValue.equals("")){
                                Property property = new Property();
                                property.setPropertyID(i);
                                property.setValue(propertyValue);
                                itemProperties.add(property);
                            }
                        }

                        if (auctionDAO.createAuction(a)) {
                            int newAuctionID = auctionDAO.getMostRecentAuction(sellerID);
                            a.setItemID(newAuctionID);
                            propertyDAO.insertProperties(newAuctionID, itemProperties);

                            request.setAttribute("message", "Auction successfully posted!");
                            request.setAttribute("auction", a);
                            request.getRequestDispatcher("newAuction.jsp").include(request, response);
                        } else {
                            request.setAttribute("message", "Auction failed.");
                            request.getRequestDispatcher("newAuction.jsp").include(request, response);
                        }


                    }else{
                        request.setAttribute("error", "Invalid Starting Bid");
                        request.getRequestDispatcher("newAuction.jsp").include(request, response);
                    }
                }
            }
        } catch (ClassNotFoundException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

}