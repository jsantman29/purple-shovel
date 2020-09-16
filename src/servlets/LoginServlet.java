import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import daos.*;
import objs.*;

@WebServlet(name = "LoginServlet")

public class LoginServlet extends HttpServlet {
	private UserDAO userDAO;
	
	public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.userDAO = new UserDAO(database);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
  
		response.setContentType("text/html");
          
		String n = request.getParameter("username");  
		String p = request.getParameter("password");  
		try {
			HttpSession session = request.getSession(true);
			User user = new User();
			user = userDAO.validate(n,p);
			
			if (user!=null) {
				
				// gets session so login attributes can be set
				
				session.setAttribute("isUser", true);
				session.setAttribute("isAdmin", user.isAdmin());
				session.setAttribute("isCSR", user.isCSR());
				session.setAttribute("userID", user.getId());
				session.setAttribute("username", user.getUsername());
				
				// fowards response to WelcomeServlet
				request.getRequestDispatcher("./").forward(request, response);
				
			} else {
				// sets message for duration of request
				request.setAttribute("message", "Invalid credentials, try again.");
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

}