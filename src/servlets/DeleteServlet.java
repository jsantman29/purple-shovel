import daos.AuctionDAO;
import daos.BidDAO;
import objs.Auction;
import objs.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteServlet")

public class DeleteServlet extends HttpServlet {
    private BidDAO bidDAO;
    private AuctionDAO auctionDAO;

    public void init() throws ServletException {			
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.auctionDAO = new AuctionDAO(database);
        this.bidDAO = new BidDAO(database);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        try {
            String deleteType = request.getParameter("deleteType");
            if(deleteType==null || deleteType.equals("")){
                return;
            }
            else if(deleteType.equals("auction")){
                auctionDAO.deleteAuction(Integer.parseInt(request.getParameter("itemID")));
                request.setAttribute("delete", "This auction has been DELETED.");
                request.getRequestDispatcher("auction").forward(request, response);
            }
            else if(deleteType.equals("bid")){
                System.out.println(request.getParameter("bidID"));
                bidDAO.deleteBid(Integer.parseInt(request.getParameter("bidID")));
                request.setAttribute("delete", "Bid has been DELETED.");
                request.getRequestDispatcher(request.getRequestURI()).include(request, response);
            }
            else{
                return;
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }

}
