import daos.*;
import objs.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "LoadAuctionServlet")

public class LoadAuctionServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        try {
            String test = request.getParameter("itemID");
            if(test==null){
                request.getRequestDispatcher("auction.jsp").include(request, response);
            }
            else {
                int itemID = Integer.parseInt(request.getParameter("itemID"));
                Auction auction = auctionDAO.retrieve(itemID);
                List<Property> properties = propertyDAO.getItemProperties(itemID);
                request.setAttribute("auction", auction);
                request.setAttribute("properties", properties);
                request.getRequestDispatcher("auction.jsp").include(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }
}
