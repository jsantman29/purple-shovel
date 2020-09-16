import daos.AuctionDAO;
import daos.PropertyDAO;
import daos.UserDAO;
import objs.Auction;
import objs.Database;
import objs.Property;
import objs.User;

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

@WebServlet(name = "EditUserServlet")

public class EditUserServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.userDAO = new UserDAO(database);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userID = Integer.parseInt(request.getParameter("userID"));
            User user = userDAO.retrieve(userID);
            request.setAttribute("user", user);
            request.getRequestDispatcher("editUser.jsp").include(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        response.setContentType("text/html");

        try {
            int userID = Integer.parseInt(request.getParameter("userID"));
            User user = userDAO.retrieve(userID);
            request.setAttribute("user", user);

            String username = request.getParameter("username");
            String isAdmin = request.getParameter("isAdmin");
            String isCSR = request.getParameter("isCSR");
            String isLocked = request.getParameter("isLocked");
            String password = request.getParameter("password");
            
            
            if(username.equals("") || isAdmin.equals("") || isCSR.equals("") || isLocked.equals("")||password.equals("")){
                request.setAttribute("error", "Empty field(s).");
                request.getRequestDispatcher("editUser.jsp").include(request, response);
            } else {

                user.setUsername(username);
                if (isAdmin.equals("true")) {
                    user.setAdmin(true);
                } else {
                    user.setAdmin(false);
                }
                if (isCSR.equals("true")) {
                    user.setCSR(true);
                } else {
                    user.setCSR(false);
                }
                if (isLocked.equals("true")) {
                    user.setLocked(true);
                } else {
                    user.setLocked(false);
                }

                user.setPassword(password);

                if (userDAO.editUser(user)) {
                    request.setAttribute("message", "User successfully updated!");
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("editUser.jsp").include(request, response);
                } else {
                    request.setAttribute("message", "Update failed.");
                    request.getRequestDispatcher("editUser.jsp").include(request, response);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}