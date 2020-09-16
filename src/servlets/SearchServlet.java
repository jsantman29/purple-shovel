import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import daos.*;
import objs.*;

@WebServlet(name = "SearchServlet")

public class SearchServlet extends HttpServlet {
    private AuctionDAO auctionDAO;
    private BidDAO bidDAO;
    private UserDAO userDAO;
    private QuestionDAO questionDAO;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.auctionDAO = new AuctionDAO(database);
        this.bidDAO = new BidDAO(database);
        this.userDAO = new UserDAO(database);
        this.questionDAO = new QuestionDAO(database);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        try {
            String typeOfSearch = request.getParameter("typeOfSearch");
            HttpSession session = request.getSession(true);
            //System.out.println(typeOfSearch);
            if(typeOfSearch.equals("userAuctions")) {
                List<Auction> myAuctions = new ArrayList<Auction>();
                String userID = request.getParameter("userID");
                //System.out.println(userID);
                myAuctions = auctionDAO.userSearch(userID);

                if(myAuctions.isEmpty()){
                    request.setAttribute("title", "My Auctions");
                    request.setAttribute("message", "You have no auctions.");
                    request.getRequestDispatcher("results.jsp").include(request, response);
                }
                else {
                    request.setAttribute("title", "My Auctions");
                    request.setAttribute("auctions", myAuctions);
                    request.getRequestDispatcher("results.jsp").include(request, response);
                }
            }
            else if(typeOfSearch.equals("all")){
                List<Auction> auctions = new ArrayList<Auction>();
                String field = request.getParameter("field");
                String parentCatID = request.getParameter("parentCatID");
                String catID = request.getParameter("catID");
                String minBid = request.getParameter("minBid");
                String maxBid = request.getParameter("maxBid");
                String opendate = request.getParameter("opendate");
                String opentime = request.getParameter("opentime");
                String enddate = request.getParameter("enddate");
                String endtime = request.getParameter("endtime");
                String sortType = request.getParameter("sortType");
                String order = request.getParameter("order");
                String alertName = request.getParameter("alertName");
                String isAlert = request.getParameter("isAlert");

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

                Object test = session.getAttribute("userID");
                String userID = null;
                if(test!=null){
                    userID = test.toString();
                }

                if((opendate==null && opentime!=null) || (enddate==null && endtime!=null)){
                    request.setAttribute("message", "You must enter a date to accompany time.");
                    request.getRequestDispatcher("search.jsp").include(request, response);
                }
                else if(sortType!=null && order==null){
                    request.setAttribute("message", "You must select an order to sort by.");
                    request.getRequestDispatcher("search.jsp").include(request, response);
                }
                else if(isAlert != null && isAlert.equals("on") && alertName.equals("")) {
                    request.setAttribute("message", "You must enter an alert name for your alert.");
                    request.getRequestDispatcher("search.jsp").include(request, response);
                }
                else if(isAlert != null && isAlert.equals("on") && !auctionDAO.validateSave(userID, alertName)) {
                    request.setAttribute("message", "You already made an alert with this name.");
                    request.getRequestDispatcher("search.jsp").include(request, response);
                }
                else {
                    auctions = auctionDAO.keywordSearch(field, parentCatID, catID, minBid, maxBid, opendate, opentime, enddate, endtime, sortType, order, itemProperties);
                    if (isAlert != null && isAlert.equals("on")) {
                        auctionDAO.saveSearch(userID, alertName, field, parentCatID, catID, minBid, maxBid, opendate, opentime, enddate, endtime);
                    }

                    if (auctions.isEmpty()) {
                        request.setAttribute("title", "Results");
                        if (isAlert != null && isAlert.equals("on")) {
                            request.setAttribute("message", "No results found. You will be alerted when a similar item becomes available.");
                        } else {
                            request.setAttribute("message", "No results found.");
                        }
                        request.getRequestDispatcher("results.jsp").include(request, response);
                    } else {
                        request.setAttribute("title", "Results");
                        request.setAttribute("auctions", auctions);
                        request.getRequestDispatcher("results.jsp").include(request, response);
                    }
                }
            }
            else if(typeOfSearch.equals("bidHistory")){
                int itemID = Integer.parseInt(request.getParameter("itemID"));
                //System.out.println(itemID);
                List<Bid> bids = new ArrayList<Bid>();
                bids = bidDAO.listAuctionBids(itemID);
                request.setAttribute("bids", bids);
                if(bids.isEmpty()){
                    request.setAttribute("error", "No bids for this auction.");
                }
                request.getRequestDispatcher("bidHistory.jsp").include(request,response);
            }
            else if(typeOfSearch.equals("similarItems")){
                int id = Integer.parseInt(request.getParameter("itemID"));
                List<Auction> auctions = new ArrayList<Auction>();
                auctions = auctionDAO.getSimilarItems(id);
                request.setAttribute("auctions", auctions);
                if(auctions.isEmpty()){
                    request.setAttribute("title", "No auctions similar to");
                }
                else{
                    request.setAttribute("title", "Showing auctions similar to");
                }
                request.getRequestDispatcher("results.jsp").include(request,response);
            }
            else if(typeOfSearch.equals("participation")){
                String username = request.getParameter("username");
                int userID = userDAO.getUserID(username);
                if(userID == 0){
                    request.setAttribute("message", "User does not exist.");
                }
                else{
                    System.out.println(userID);
                    List<Auction> bidIn = auctionDAO.userAsBuyer(userID);
                    List<Auction> soldIn = auctionDAO.userAsSeller(userID);
                    request.setAttribute("bidIn", bidIn);
                    request.setAttribute("soldIn", soldIn);
                    request.setAttribute("title", "Auction Participation for");
                }
                request.getRequestDispatcher("results.jsp").include(request,response);
            }
            else if(typeOfSearch.equals("question")) {
                //System.out.println("test");
                String type = request.getParameter("searchType");
                List<Question> questions = new ArrayList<Question>();

                if (type == null || type.equals("")) {
                    questions = new ArrayList<Question>();
                } else if (type.equals("all")) {
                    String parameter = request.getParameter("searchText");
                    questions = questionDAO.searchQuestions(type, parameter);
                } else if (type.equals("questions")){
                    String parameter = request.getParameter("questionText");
                    questions = questionDAO.searchQuestions(type, parameter);
                } else if(type.equals("answers")){
                    String parameter = request.getParameter("answerText");
                    questions = questionDAO.searchQuestions(type, parameter);
                }
                else if(type.equals("userID")){
                    String parameter = request.getParameter("userID");
                    questions = questionDAO.searchQuestions(type, parameter);
                }

                request.setAttribute("questionList", questions);

                if(questions.isEmpty()){
                    request.setAttribute("error", "No results for this search.");
                }
                request.getRequestDispatcher("questionBoard.jsp").include(request,response);
            }
            else if(typeOfSearch.equals("user")){
                String type = request.getParameter("searchType");
                String parameter = null;
                List<User> users = new ArrayList<User>();

                if (type == null || type.equals("")) {
                    users = new ArrayList<User>();
                } else if (type.equals("username")) {
                    parameter = request.getParameter("username");
                    if(parameter==null||parameter.equals("")){
                        request.setAttribute("error", "No users with username "+parameter+" were found.");
                    }
                    else {
                        users.add(userDAO.retrieveName(parameter));
                    }
                }
                else if(type.equals("userID")){
                    parameter = request.getParameter("userID");
                    if(parameter==null||parameter.equals("")){
                        request.setAttribute("error", "No users with userID "+parameter+" were found.");
                    }
                    else {
                        users.add(userDAO.retrieve(Integer.parseInt(parameter)));
                    }
                }

                request.setAttribute("users", users);

                if(users.isEmpty()&&type.equals("username")){
                    request.setAttribute("error", "No users with username "+parameter+" were found.");
                }
                if(users.isEmpty()&&type.equals("userID")){
                    request.setAttribute("error", "No users with userID "+parameter+" were found.");
                }
                request.getRequestDispatcher("userResults.jsp").include(request,response);
            }
            else if(typeOfSearch.equals("manageAuctions")){
                String type = request.getParameter("manageAuctions");
                request.setAttribute("message", "Find an auction to manage!");
                request.getRequestDispatcher("search.jsp").include(request, response);
            }
            else{
                request.getRequestDispatcher("/start").include(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }

}