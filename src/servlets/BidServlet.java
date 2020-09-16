import daos.AuctionDAO;
import daos.BidDAO;
import daos.UserDAO;
import objs.Auction;
import objs.Bid;
import objs.Database;
import objs.User;
//import org.apache.tomcat.util.buf.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "BidServlet")

public class BidServlet extends HttpServlet {
    private BidDAO bidDAO;
    private AuctionDAO auctionDAO;

    public void init() throws ServletException {			
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.bidDAO = new BidDAO(database);
        this.auctionDAO = new AuctionDAO(database);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        response.setContentType("text/html");

       //do the add stuff
        //ADD
        try {
            Bid b = new Bid();
            float bid;
            float maxBid;
            int userID = (Integer) session.getAttribute("userID");
            String userName = (String) session.getAttribute("username");
            String Bid = request.getParameter("bid");
            String MaxBid = request.getParameter("maxBid");
            Date curDate = new Date(System.currentTimeMillis());
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Auction auction = auctionDAO.retrieve(itemID);
            //set for later to be passed back
            request.setAttribute("itemID", itemID);


            if(auction != null && !Bid.isEmpty()){
                if (MaxBid.isEmpty()){
                    bid = Float.valueOf(Bid);
                    maxBid = 0;
                }else{
                    bid = Float.valueOf(Bid);
                    maxBid = Float.valueOf(MaxBid);
                }

                b.setUserID(userID);
                b.setItemID(itemID);
                b.setUsername(userName);
                b.setAutoBid(false);
                b.setBid(bid);
                b.setBidTimestamp(curDate);

                if(bid != 0 && auction.isOpen() && auction.getEnd().after(curDate))  {
                    if(maxBid != 0){
                        b.setBidMax(maxBid);
                        //if new bid with max
                        if (auction.getCurrentBid() == 0) {
                            if (bid >= (auction.getStartingBid()) && maxBid >= bid) {
                                //do bid, manual and max
                                bidDAO.addBid(b);
                                auction.setCurrentBid(bid);
                                request.setAttribute("auction", auction);
                                request.setAttribute("message", "Bid successfully posted! Check bid history to see current winner.");
                                request.getRequestDispatcher("auction.jsp").include(request, response);
                            } else {
                                //bid failed < starting + increment or max bid too low
                                request.setAttribute("error", "Invalid bid(s).");
                                request.getRequestDispatcher("auction.jsp").include(request, response);
                            }
                            //if not new bid with max
                        } else if (bid >= (auction.getCurrentBid() + auction.getBidIncrement()) && maxBid >= bid) {
                            //do bid, manual and max
                            auction.setCurrentBid(bid);
                            bidDAO.addBid(b);
                            request.setAttribute("auction", auction);
                            request.setAttribute("message", "Bid successfully posted! Check bid history to see current winner.");
                            request.getRequestDispatcher("auction.jsp").include(request, response);
                        } else {
                            //bid failed < current + increment or max bid too low
                            request.setAttribute("error", "Invalid bid(s).");
                            request.getRequestDispatcher("auction.jsp").include(request, response);
                        }
                    //set manual bid, max = manual
                    }else {
                        b.setBidMax(bid);
                        //if new bid no max
                        if (auction.getCurrentBid() == 0) {
                            if (bid >= (auction.getStartingBid())) {
                                //do bid, manual = max
                                auction.setCurrentBid(bid);
                                bidDAO.addBid(b);
                                request.setAttribute("auction", auction);
                                request.setAttribute("message", "Bid successfully posted! Check bid history to see current winner.");
                                request.getRequestDispatcher("auction.jsp").include(request, response);
                            } else {
                                //bid failed < starting + increment
                                request.setAttribute("error", "Invalid bid(s).");
                                request.getRequestDispatcher("auction.jsp").include(request, response);
                            }
                            //if not new bid no max
                        } else if (bid >= (auction.getCurrentBid() + auction.getBidIncrement())) {
                            //do bid, manual = max
                            bidDAO.addBid(b);
                            auction.setCurrentBid(bid);
                            request.setAttribute("auction", auction);
                            request.setAttribute("message", "Bid successfully posted! Check bid history to see current winner.");
                            request.getRequestDispatcher("auction.jsp").include(request, response);
                        } else {
                            //bid failed < current + increment
                            request.setAttribute("error", "Invalid bid(s).");
                            request.getRequestDispatcher("auction.jsp").include(request, response);
                        }
                    }
                }else{
                    //bid failed, not open or no bid
                    request.setAttribute("error", "Invalid bid(s).");
                    request.getRequestDispatcher("auction.jsp").include(request, response);
                }
            }else{
                request.setAttribute("error", "Bid Failed");
                request.getRequestDispatcher("auction.jsp").include(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }

    }

}
